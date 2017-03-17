package com.fstyle.structure_android.utils.validator;

import android.support.annotation.StringRes;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by le.quang.dao on 17/03/2017.
 */

@Documented
@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Rule {
    @ValidType int[] types();

    @StringRes int message() default -1;
}
