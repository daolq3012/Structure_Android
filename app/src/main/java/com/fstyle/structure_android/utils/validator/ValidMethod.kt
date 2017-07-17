package com.fstyle.structure_android.utils.validator

/**
 * Created by le.quang.dao on 17/03/2017.
 */

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ValidMethod(val type: IntArray)
