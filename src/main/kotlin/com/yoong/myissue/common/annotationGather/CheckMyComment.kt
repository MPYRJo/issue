package com.yoong.myissue.common.annotationGather

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckMyComment(
    val crudType: String
)
