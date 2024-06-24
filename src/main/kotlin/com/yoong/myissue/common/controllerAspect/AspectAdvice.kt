package com.yoong.myissue.common.controllerAspect

import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.infra.security.config.UserPrincipal
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component


@Aspect
@Component
class AspectAdvice {

    @Before("@annotation(com.yoong.myissue.common.annotationGather.FailedLogin)")
    fun failedLogin(joinPoint: JoinPoint) {
        joinPoint.args[0] as UserPrincipal? ?: throw InvalidCredentialException("로그인을 해 주세요")
    }
}