package com.yoong.myissue.common.annotation

import com.yoong.myissue.common.enum.AuthenticationType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckAuthentication(
    val authenticationType: AuthenticationType,
)

