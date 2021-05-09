package com.ccc.nameapp.data.source.remote.api.middleware

import android.text.TextUtils
import android.util.Log
import com.ccc.nameapp.data.source.remote.api.error.BaseException
import com.ccc.nameapp.data.source.remote.api.error.ErrorResponse
import com.google.gson.Gson
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * ErrorHandling:
 * http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 * This class only for Call in retrofit 2
 */
class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        return RxCallAdapterWrapper(
            returnType,
            wrapped = original.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>
        )
    }

    /**
     * RxCallAdapterWrapper
     */
    internal inner class RxCallAdapterWrapper(
        private val returnType: Type,
        private val wrapped: CallAdapter<Any, Any>?
    ) : CallAdapter<Any, Any> {

        override fun responseType(): Type? {
            return wrapped?.responseType()
        }

        override fun adapt(call: Call<Any>): Any? {
            val rawType = getRawType(returnType)

            val isFlowable = rawType == Flowable::class.java
            val isSingle = rawType == Single::class.java
            val isMaybe = rawType == Maybe::class.java
            val isCompletable = rawType == Completable::class.java
            val isInvalidObject = rawType != Observable::class.java && !isFlowable && !isSingle && !isMaybe
            if (isInvalidObject) {
                return null
            }
            if (returnType !is ParameterizedType) {
                val name = when {
                    isFlowable -> "Flowable"
                    isSingle -> "Single"
                    isMaybe -> "Maybe"
                    else -> "Observable"
                }
                val errorMessage = "$name return type must be parameterized as $name <Foo> or $name <? extends Foo>"
                throw IllegalStateException(errorMessage)
            }

            return when {
                isFlowable -> (wrapped?.adapt(call) as Flowable<*>).onErrorResumeNext { throwable: Throwable ->
                    Flowable.error(
                        convertToBaseException(throwable)
                    )
                }
                isSingle -> (wrapped?.adapt(call) as Single<*>).onErrorResumeNext { throwable ->
                    Single.error(
                        convertToBaseException(throwable)
                    )
                }
                isMaybe -> (wrapped?.adapt(call) as Maybe<*>).onErrorResumeNext { throwable: Throwable ->
                    Maybe.error(
                        convertToBaseException(throwable)
                    )
                }
                isCompletable -> (wrapped?.adapt(call) as Completable).onErrorResumeNext { throwable ->
                    Completable.error(
                        convertToBaseException(throwable)
                    )
                }
                else -> (wrapped?.adapt(call) as Observable<*>).onErrorResumeNext { throwable: Throwable ->
                    Observable.error(
                        convertToBaseException(throwable)
                    )
                }
            }
        }

        private fun convertToBaseException(throwable: Throwable): BaseException {
            if (throwable is BaseException) {
                return throwable
            }

            if (throwable is IOException) {
                return BaseException.toNetworkError(throwable)
            }

            if (throwable is HttpException) {
                val response = throwable.response()
                if (response.code() in 500..511) {
                    return try {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                        if (errorResponse != null && !TextUtils.isEmpty(errorResponse.message)) {
                            BaseException.toServerError(errorResponse)
                        } else {
                            BaseException.toHttpError(response)
                        }
                    } catch (e: IOException) {
                        Log.e(TAG, e.message ?: "null")
                        BaseException.toUnexpectedError(throwable)
                    }
                } else {
                    return BaseException.toHttpError(response)
                }
            }

            return BaseException.toUnexpectedError(throwable)
        }
    }

    companion object {

        private val TAG = RxErrorHandlingCallAdapterFactory::class.java.name

        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }
}
