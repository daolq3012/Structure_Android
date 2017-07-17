package com.fstyle.structure_android.utils.validator

import android.support.annotation.StringRes

/**
 * Created by le.quang.dao on 17/03/2017.
 */

@MustBeDocumented
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Rule(val types: IntArray, @StringRes val message: Int = -1)
