package com.yoong.myissue.common.serviceAspect

import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.common.enumGather.AuthenticationType
import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.exception.clazz.NoAuthenticationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
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
    fun `checkAuthentication 에서 user_role 과 실제 role 규칙이 다를 경우 NoAuthenticationException 애러 확인`(){

        val member = Member(
            nickname = "test",
            email = "test@test.com",
            password = "test",
            role = Role.USER,
            team = Team(
                name = "test",
                issues = listOf(),
                members = listOf()
            )
        )

       every { memberService.searchEmail(any()) } returns member


       shouldThrow<NoAuthenticationException> {
           validAuthentication.role(member.getRole(), AuthenticationType.ADMIN)
       }.let {
           it.message shouldBe "권한이 없습니다"
       }


    }

    @Test
    fun `checkDummyTeam 에서 팀 아이디가 DummyTeam과 같을 경우 DummyTeamException 애러 확인`(){

        val team = Team(
            name = "test",
            issues = listOf(),
            members = listOf()
        )

        every { teamService. }


    }
}