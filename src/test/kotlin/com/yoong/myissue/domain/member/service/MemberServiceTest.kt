package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.dto.SignupRequest
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.repository.MemberRepository
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.domain.team.service.TeamService
import com.yoong.myissue.infra.security.jwt.PasswordEncoder
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class MemberServiceTest {

    private val memberRepository = mockk<MemberRepository>()
    private val teamRepository = mockk<TeamRepository>()
    private val teamService = TeamService(teamRepository)
    private val memberService = MemberService(memberRepository, teamService, PasswordEncoder())


    @Test
    fun `정상적으로 SingUp 이 되었을 경우 "회원 가입이 완료 되었습니다!!" 를 반환`(){
        val signupRequest = SignupRequest(
            nickname = "test",
            email = "test@test.com",
            password = "test",
            password2 = "test"
        )

        var team = Team(
            name="test",
            issues = mutableListOf(),
            members = mutableListOf(),
        )

        every { memberRepository.existsByEmail(any()) } returns false
        every { memberRepository.existsByNickname(any()) } returns false
        every { teamRepository.findByIdOrNull(any()) } answers {
            team.id = 1L
            team
        }
        memberService.matchPassword(signupRequest.password2, signupRequest.password) shouldBe Unit

        every { memberRepository.save(any()) }returns Member(
            nickname = "test",
            email = "test@test.com",
            password = "test",
            role = Role.USER,
            team = team
        )

        val result = memberService.signup(signupRequest)

        result shouldBe "회원 가입이 완료 되었습니다!!"
    }


}