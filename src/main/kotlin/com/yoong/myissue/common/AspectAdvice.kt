package com.yoong.myissue.common

import com.yoong.myissue.common.annotation.CheckAuthentication
import com.yoong.myissue.common.`class`.ValidAuthentication
import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.service.DUMMY_TEAM
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.exception.`class`.DummyTeamException
import org.apache.juli.logging.LogFactory
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class AspectAdvice(
    private val validAuthentication: ValidAuthentication,
    private val memberService: ExternalMemberService,
) {

    @Before("@annotation(com.yoong.myissue.common.annotation.CheckAuthentication)")
    fun checkAuthentication(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val annotation = method.getAnnotation(CheckAuthentication::class.java)

        val authenticationType = annotation.authenticationType

        val member = memberService.searchEmail(joinPoint.args[1].toString())

        validAuthentication.role(member.getRole(), authenticationType)
    }

    @Before("@annotation(com.yoong.myissue.common.annotation.CheckDummyTeam)")
    fun checkDummyTeam(joinPoint: JoinPoint) {

        val teamId = joinPoint.args[0] as Long

        if(teamId == DUMMY_TEAM) throw DummyTeamException()
    }

}