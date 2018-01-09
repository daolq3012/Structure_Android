package com.fstyle.structure_android.data.source.remote_api.middleware;

import android.text.TextUtils;
import android.util.Log;
import com.fstyle.structure_android.data.source.remote_api.error.BaseException;
import com.fstyle.structure_android.data.source.remote_api.response.ErrorResponse;
import com.google.gson.Gson;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.reactivestreams.Publisher;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * ErrorHandling:
 * http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 * This class only for Call in retrofit 2
 */
public final class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private static final String TAG = RxErrorHandlingCallAdapterFactory.class.getName();

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(returnType,
                original.get(returnType, annotations, retrofit));
    }

    /**
     * RxCallAdapterWrapper
     */
    class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Type returnType;
        private final CallAdapter<R, Object> wrapped;

        RxCallAdapterWrapper(Type type, CallAdapter<R, Object> wrapped) {
            returnType = type;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        public Object adapt(Call<R> call) {
            Class<?> rawType = getRawType(returnType);

            boolean isFlowable = rawType == Flowable.class;
            boolean isSingle = rawType == Single.class;
            boolean isMaybe = rawType == Maybe.class;
            boolean isCompletable = rawType == Completable.class;
            if (rawType != Observable.class && !isFlowable && !isSingle && !isMaybe) {
                return null;
            }
            if (!(returnType instanceof ParameterizedType)) {
                String name = isFlowable ? "Flowable"
                        : isSingle ? "Single" : isMaybe ? "Maybe" : "Observable";
                throw new IllegalStateException(name
                        + " return type must be parameterized"
                        + " as "
                        + name
                        + "<Foo> or "
                        + name
                        + "<? extends Foo>");
            }

            if (isFlowable) {
                return ((Flowable) wrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, Publisher>() {
                            @Override
                            public Publisher apply(Throwable throwable) throws Exception {
                                return Flowable.error(convertToBaseException(throwable));
                            }
                        });
            }
            if (isSingle) {
                return ((Single) wrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, SingleSource>() {
                            @Override
                            public SingleSource apply(Throwable throwable) throws Exception {
                                return Single.error(convertToBaseException(throwable));
                            }
                        });
            }
            if (isMaybe) {
                return ((Maybe) wrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, MaybeSource>() {
                            @Override
                            public MaybeSource apply(Throwable throwable) throws Exception {
                                return Maybe.error(convertToBaseException(throwable));
                            }
                        });
            }
            if (isCompletable) {
                return ((Completable) wrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, CompletableSource>() {
                            @Override
                            public CompletableSource apply(Throwable throwable) throws Exception {
                                return Completable.error(convertToBaseException(throwable));
                            }
                        });
            }
            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(
                    new Function<Throwable, ObservableSource>() {
                        @Override
                        public ObservableSource apply(Throwable throwable) throws Exception {
                            return Observable.error(convertToBaseException(throwable));
                        }
                    });
        }

        private BaseException convertToBaseException(Throwable throwable) {
            if (throwable instanceof BaseException) {
                return (BaseException) throwable;
            }

            if (throwable instanceof IOException) {
                return BaseException.toNetworkError(throwable);
            }

            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                if (response.errorBody() != null) {
                    try {
                        ErrorResponse errorResponse =
                                new Gson().fromJson(response.errorBody().string(),
                                        ErrorResponse.class);
                        if (errorResponse != null && !TextUtils.isEmpty(
                                errorResponse.getMessage())) {
                            return BaseException.toServerError(errorResponse);
                        } else {
                            return BaseException.toHttpError(response);
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                        return BaseException.toUnexpectedError(throwable);
                    }
                } else {
                    return BaseException.toHttpError(response);
                }
            }

            return BaseException.toUnexpectedError(throwable);
        }
    }
}
