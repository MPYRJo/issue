package com.yoong.myissue.common.annotationGather

import com.yoong.myissue.common.enumGather.AuthenticationType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckAuthentication(
    val authenticationType: AuthenticationType,
)

