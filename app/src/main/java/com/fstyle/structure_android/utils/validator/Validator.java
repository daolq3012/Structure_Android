package com.fstyle.structure_android.utils.validator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 16/03/2017.
 */

public class Validator {
    private static final String TAG = Validator.class.getName();

    private Context mContext;
    private SparseArray<Method> mValidatedMethods;
    private String mMessage;

    private SparseArray<Integer> mAllErrorMessage;

    @Nullable
    private Pattern mNGWordPattern;

    /**
     * @param context Application context
     */
    public Validator(@ApplicationContext Context context, Class clzz) {
        if (context instanceof Activity) {
            throw new ValidationException(
                    "Context should be get From Application to avoid leak memory");
        }
        mContext = context;
        mValidatedMethods = cacheValidatedMethod();
        mAllErrorMessage = getAllErrorMessage(clzz);
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
        @SuppressLint("UseSparseArrays") SparseArray<Integer> errorMessages = new SparseArray<>();
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

    public <T extends BaseViewModel> boolean validateAll(T object) throws IllegalAccessException {
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

            Object obj = field.get(object);
            boolean valid = validate(obj, rules, isOptional);
            if (!valid) {
                isValid = false;
            }
        }
        return isValid;
    }

    public Subscription initNGWordPattern() {
        return Observable.create((Observable.OnSubscribe<Pattern>) subscriber -> {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(mContext.getAssets().open("ng-word")));
                StringBuilder buffer = new StringBuilder();
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
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pattern -> mNGWordPattern = pattern, throwable -> {
                    throw new ValidationException("Error when open ng-word", throwable);
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
                !TextUtils.isEmpty(str) && !mNGWordPattern.matcher(str.toLowerCase(Locale.ENGLISH))
                        .find();
        mMessage = isValid ? "" : mContext.getString(mAllErrorMessage.valueAt(ValidType.NG_WORD));
        return mMessage;
    }

    @ValidMethod(type = { ValidType.VALUE_RANGE_0_100 })
    public String validateValueRangeFrom0to100(String str) {
        int value = convertStringToInteger(str);
        boolean isValid = value >= 0 && value <= 100;
        mMessage = isValid ? ""
                : mContext.getString(mAllErrorMessage.valueAt(ValidType.VALUE_RANGE_0_100));
        return mMessage;
    }

    @ValidMethod(type = { ValidType.NON_EMPTY })
    public String validateValueNonEmpty(String value) {
        boolean isValid = !TextUtils.isEmpty(value);
        mMessage = isValid ? "" : mContext.getString(mAllErrorMessage.valueAt(ValidType.NON_EMPTY));
        return mMessage;
    }

    /**
     * @return Integer.MIN_VALUE if convert error
     */
    private int convertStringToInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
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
