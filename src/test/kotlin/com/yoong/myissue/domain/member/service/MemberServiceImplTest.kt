package com.yoong.myissue.domain.member.service

import com.yoong.myissue.common.clazz.PasswordManagement
import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.member.dto.LoginRequest
import com.yoong.myissue.domain.member.dto.LoginResponse
import com.yoong.myissue.domain.member.dto.SignupRequest
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.repository.MemberRepository
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.domain.team.service.TeamServiceImpl
import com.yoong.myissue.infra.security.jwt.JwtPlugin
import com.yoong.myissue.infra.security.jwt.PasswordEncoder
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class MemberServiceImplTest {


    private val memberRepository = mockk<MemberRepository>()
    private val externalMemberService = ExternalMemberService(memberRepository)
    private val teamRepository = mockk<TeamRepository>()
    private val externalTeamService = ExternalTeamService(teamRepository)
    private val passwordEncoder = PasswordEncoder()
    private val passwordManagement = PasswordManagement(passwordEncoder)
    private val jwtPlugin = mockk<JwtPlugin>()
    private val memberService = MemberServiceImpl(memberRepository, externalTeamService, passwordManagement, jwtPlugin)
    private val teamService = TeamServiceImpl(teamRepository, externalMemberService, externalTeamService)

    @Test
    fun `정상적으로 SingUp 이 되었을 경우 "회원 가입이 완료 되었습니다!!" 를 반환`(){

        val signupRequest = SignupRequest(
            nickname = "test",
            email = "test@test.com",
            password = "test",
            password2 = "test"
        )

        val team = Team(
            name="test",
            issues = mutableListOf(),
            members = mutableListOf(),
        )

        every { memberRepository.existsByEmail(any()) } returns false
        every { memberRepository.existsByNickname(any()) } returns false
        every { teamRepository.findByIdOrNull(any()) } answers {
            team.getId()
            team
        }
        passwordManagement.isSame(signupRequest.password,signupRequest.password2)

        every { memberRepository.save(any()) }returns MEMBER

        val result = memberService.signup(signupRequest)

        result shouldBe "회원 가입이 완료 되었습니다!!"
    }

    @Test
    fun `정상적으로 로그인 이 되었을 경우 AccessToken을 반환`(){ // MemberServiceImplTest.kt:81 에서 NPE 를 뱉어내는데 왜 반환하는지 모르겠습니다

        val loginRequest = LoginRequest(
            email = "test@test.com",
            password = "test",
        )




        every { memberRepository.findByEmail(any()) } returns MEMBER
        every { MEMBER.validPassword(any(), any()) } returns true


        val loginResponse = LoginResponse(
            loginRequest.email,
            MEMBER.getAccessToken(jwtPlugin)
        )

        every { jwtPlugin.generateAccessToken(any(), any(), any())} returns "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaXNzIjoibXlpc3N1ZS55b29uZy5jb20iLCJpYXQiOjE3MTk0NDk4MTIsImV4cCI6MTcyMDA1NDYxMiwicm9sZSI6IlVTRVIiLCJlbWFpbCI6InNpbmJAZ21haWwuY29tIn0.9xNfI_xc9TQcs95nYqsPCY699AK8AB_UviaHV-6KQCQ"

        val result = memberService.login(loginRequest)

        result shouldBe loginResponse

    }


    companion object{
        private val TEAM = Team(
            name="test",
            issues = mutableListOf(),
            members = mutableListOf(),
        )
        private val MEMBER = Member(
            nickname = "test",
            email = "test@test.com",
            password = "test",
            role = Role.USER,
            team = TEAM
        )
    }

}