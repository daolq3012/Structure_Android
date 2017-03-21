package com.fstyle.structure_android.utils.validator;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.BaseModel;
import com.fstyle.structure_android.screen.BaseViewModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.regex.Pattern;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 16/03/2017.
 */

public class Validator {
    private static final String TAG = Validator.class.getName();

    private Context mContext;
    private SparseArray<Method> mValidatedMethods;
    private BaseModel mModelCache;

    private BaseViewModel mViewCache;

    private SparseArray<Integer> mAllErrorMessage;

    @Nullable
    private Pattern mNGWordPattern;

    /**
     * @param context Application context
     * @param clzz View
     * @param <T> Class extend from {@link BaseViewModel}
     */
    public <T extends BaseViewModel> Validator(@ApplicationContext Context context, T clzz) {
        if (context instanceof Activity) {
            throw new ValidationException(
                    "Context should be get From Application to avoid leak memory");
        }
        mContext = context;
        mValidatedMethods = cacheValidatedMethod();
        mAllErrorMessage = getAllErrorMessage(clzz.getClass());
    }

    /**
     * @param context Application context
     * @param clzz View
     * @param <T> Class extend from {@link BaseModel}
     */
    public <T extends BaseModel> Validator(@ApplicationContext Context context, T clzz) {
        if (context instanceof Activity) {
            throw new ValidationException(
                    "Context should be get From Application to avoid leak memory");
        }
        mContext = context;
        mValidatedMethods = cacheValidatedMethod();
        mAllErrorMessage = getAllErrorMessage(clzz.getClass());
    }

    private SparseArray<Method> cacheValidatedMethod() {
        SparseArray<Method> methods = new SparseArray<>();
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ValidMethod.class)) {
                ValidMethod validMethod = method.getAnnotation(ValidMethod.class);
                for (int type : validMethod.type()) {
                    methods.put(type, method);
                }
            }
        }
        return methods;
    }

    private SparseArray<Integer> getAllErrorMessage(Class clzz) {
        SparseArray<Integer> errorMessages = new SparseArray<>();
        for (Field field : clzz.getDeclaredFields()) {
            Validation annotation = field.getAnnotation(Validation.class);
            if (annotation == null) {
                continue;
            }
            Rule[] rules = annotation.value();
            field.setAccessible(true);
            for (Rule rule : rules) {
                for (int type : rule.types()) {
                    errorMessages.put(type, rule.message());
                }
            }
        }
        return errorMessages;
    }

    /**
     * Check if an object in Validator Model with annotation {@link Optional} is valid
     */
    private boolean isValidOptional(Object factor) {
        if (factor instanceof Integer) {
            return (Integer) factor == 0;
        }
        if (factor instanceof Long) {
            return (Long) factor == 0;
        }
        if (factor instanceof Float) {
            return (Float) factor == 0.0f;
        }
        if (factor instanceof Double) {
            return (Double) factor == 0.0f;
        }
        if (factor instanceof String) {
            return TextUtils.isEmpty((String) factor);
        }
        return (factor instanceof Boolean && !((Boolean) factor)) || factor == null;
    }

    /**
     * Validate all field.
     */
    private boolean validate(Object factor, Rule[] rules, boolean isOptional) {
        boolean isValid = true;
        for (Rule rule : rules) {
            for (int type : rule.types()) {
                Method method = mValidatedMethods.get(type);
                if (method == null) {
                    continue;
                }
                try {
                    boolean valid = (isOptional && isValidOptional(factor)) || TextUtils.isEmpty(
                            (String) method.invoke(this, factor));
                    if (!valid) {
                        isValid = false;
                    }
                } catch (IllegalAccessException e) {
                    isValid = false;
                    Log.e(TAG, "validate: ", e);
                } catch (InvocationTargetException e) {
                    isValid = false;
                    Log.e(TAG, "validate: ", e);
                }
            }
        }
        return isValid;
    }

    private <T extends BaseModel, V extends BaseViewModel> boolean validateAll(T model, V view,
            boolean onlyValidateChange) {

        Object object = model != null ? model : view;

        boolean isValid = true;

        for (Field field : object.getClass().getDeclaredFields()) {
            Validation annotation = field.getAnnotation(Validation.class);
            if (annotation == null) {
                continue;
            }
            Rule[] rules = annotation.value();
            Optional optional = field.getAnnotation(Optional.class);
            boolean isOptional = optional != null;
            field.setAccessible(true);

            try {
                Object cache = null;
                if (mModelCache != null || mViewCache != null) {
                    cache = field.get(model != null ? mModelCache : mViewCache);
                }
                Object real = field.get(object);
                // detect when data has changed only if onlyValidateTheChange is true.
                // Otherwise, always run validation on all of fields .
                if (!onlyValidateChange || (cache == null && real != null) || (cache != null
                        && cache.equals(real))) {
                    boolean valid = validate(real, rules, isOptional);
                    if (!valid) {
                        isValid = false;
                    }
                }
            } catch (IllegalAccessException e) {
                Log.e(TAG, "validate: ", e);
            }
            try {
                if (model != null) {
                    mModelCache = model.clone();
                }
                mViewCache = view;
            } catch (CloneNotSupportedException e) {
                mModelCache = null;
                mViewCache = null;
                Log.e(TAG, "validate: ", e);
            }
        }
        return isValid;
    }

    public <T extends BaseModel> boolean validateAll(T model, boolean onlyValidateChange) {
        return validateAll(model, null, onlyValidateChange);
    }

    public <V extends BaseViewModel> boolean validateAll(V view, boolean onlyValidateChange) {
        return validateAll(null, view, onlyValidateChange);
    }

    public <T extends BaseModel> Validator prepare(T model) {
        try {
            mModelCache = model.clone();
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, "prepare: ", e);
        }
        return this;
    }

    @StringRes
    public int getErrorByTypeInt(@ValidType int type) {
        int index = mAllErrorMessage.indexOfValue(type);
        if (index == -1) {
            return R.string.empty;
        }
        return mAllErrorMessage.keyAt(index);
    }

    public String getErrorByType(@ValidType int type) {
        int index = mAllErrorMessage.indexOfValue(type);
        if (index == -1 || mAllErrorMessage.keyAt(index) == -1) {
            return null;
        }
        return mContext.getString(mAllErrorMessage.keyAt(index));
    }

    public void initNGWordPattern() {
        Observable.create(new Observable.OnSubscribe<Pattern>() {
            @Override
            public void call(Subscriber<? super Pattern> subscriber) {
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(mContext.getAssets().open("ng-word")));
                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (buffer.length() == 0) {
                            buffer.append("(").append(line.toLowerCase(Locale.ENGLISH));
                        } else {
                            buffer.append("|").append(line.toLowerCase(Locale.ENGLISH));
                        }
                    }
                    subscriber.onNext(Pattern.compile(buffer.append(")").toString()));
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Pattern>() {
                    @Override
                    public void call(Pattern pattern) {
                        mNGWordPattern = pattern;
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throw new ValidationException("Error when open ng-word", throwable);
                    }
                });
    }

    @ValidMethod(type = { ValidType.NG_WORD })
    public String validateNGWord(String str) {
        if (mNGWordPattern == null) {
            throw new ValidationException(
                    "NGWordPattern is null!!! \n Call initNGWordPattern() before call this method!",
                    new NullPointerException());
        }
        boolean isValid =
                TextUtils.isEmpty(str) || !mNGWordPattern.matcher(str.toLowerCase(Locale.ENGLISH))
                        .find();
        return isValid ? "" : mContext.getString(mAllErrorMessage.valueAt(ValidType.NG_WORD));
    }

    @ValidMethod(type = { ValidType.VALUE_RANGE_0_100 })
    public String validateValueRangeFrom0to100(int value) {
        boolean isValid = value >= 0 && value <= 100;
        return isValid ? "" : value == Integer.MIN_VALUE ? validateValueNonEmpty(value)
                : mContext.getString(mAllErrorMessage.valueAt(ValidType.VALUE_RANGE_0_100));
    }

    @ValidMethod(type = { ValidType.NON_EMPTY })
    public String validateValueNonEmpty(Object value) {
        boolean isValid = !TextUtils.isEmpty(value.toString());
        if (value instanceof Integer) {
            isValid = (Integer) value != Integer.MIN_VALUE;
        }
        return isValid ? "" : mContext.getString(mAllErrorMessage.valueAt(ValidType.NON_EMPTY));
    }

    /**
     * ValidationException
     */
    private static class ValidationException extends RuntimeException {
        ValidationException(String detailMessage) {
            super(detailMessage);
        }

        ValidationException(String detailMessage, Throwable exception) {
            super(detailMessage, exception);
        }
    }
}
