package com.yoong.myissue.common.serviceAspect

import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.member.service.MemberService
import com.yoong.myissue.domain.team.service.ExternalTeamService
import io.mockk.mockk
import org.aspectj.lang.JoinPoint
import kotlin.test.Test

class ServiceAspectAdviceTest {


    private val memberService = mockk<ExternalMemberService>()
    private val teamService = mockk<ExternalTeamService>()
    private val validAuthentication = ValidAuthentication()
    private val serviceAspectAdvice = ServiceAspectAdvice(
        memberService = memberService,
        teamService = teamService,
        validAuthentication = validAuthentication
    )




    @Test
    fun `checkAuthentication 에서 user_role 과 실제 role 규칙이 다를 경우 403 애러 확인`(){

        serviceAspectAdvice.checkAuthentication()
    }
}