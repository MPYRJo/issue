package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.common.clazz.PasswordManagement
import com.yoong.myissue.domain.member.dto.SignupRequest
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.repository.MemberJpaRepository
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamJpaRepository
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.domain.team.service.TeamServiceImpl
import com.yoong.myissue.infra.security.jwt.JwtPlugin
import com.yoong.myissue.infra.security.jwt.PasswordEncoder
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class MemberServiceImplTest {


//    private val memberRepository = mockk<MemberJpaRepository>()
//    private val externalMemberService = ExternalMemberService(memberRepository)
//    private val teamRepository = mockk<TeamJpaRepository>()
//    private val externalTeamService = ExternalTeamService(teamRepository)
//    private val passwordEncoder = PasswordEncoder()
//    private val passwordManagement = PasswordManagement(passwordEncoder)
//    private val jwtPlugin = mockk<JwtPlugin>()
//    private val memberService = MemberServiceImpl(memberRepository, externalTeamService, passwordManagement, jwtPlugin)
//    private val teamService = TeamServiceImpl(teamRepository, externalMemberService, externalTeamService)

//    @Test
//    fun `정상적으로 SingUp 이 되었을 경우 "회원 가입이 완료 되었습니다!!" 를 반환`(){
//
//        val signupRequest = SignupRequest(
//            nickname = "test",
//            email = "test@test.com",
//            password = "test",
//            password2 = "test"
//        )
//
//        val team = Team(
//            name="test",
//            issues = mutableListOf(),
//            members = mutableListOf(),
//        )
//
//        every { memberRepository.existsByEmail(any()) } returns false
//        every { memberRepository.existsByNickname(any()) } returns false
//        every { teamRepository.findByIdOrNull(any()) } answers {
//            team.getId()
//            team
//        }
//        passwordManagement.isSame(signupRequest.password,signupRequest.password2)
//
//        every { memberRepository.save(any()) }returns Member(
//            nickname = "test",
//            email = "test@test.com",
//            password = "test",
//            role = Role.USER,
//            team = team
//        )
//
//        val result = memberService.signup(signupRequest)
//
//        result shouldBe "회원 가입이 완료 되었습니다!!"
//    }


}