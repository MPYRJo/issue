package com.yoong.myissue.domain.team.service

import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.common.enumGather.AuthenticationType
import com.yoong.myissue.common.serviceAspect.ServiceAspectAdvice
import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.exception.clazz.DummyTeamException
import com.yoong.myissue.exception.clazz.DuplicatedModelException
import io.kotest.assertions.any
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class TeamServiceTest {

    private val teamRepository = mockk<TeamRepository>()
    private val memberService = mockk<ExternalMemberService>()
    private val externalTeamService = mockk<ExternalTeamService>()
    private val validAuthentication = ValidAuthentication()
    val teamService: TeamServiceImpl = TeamServiceImpl(teamRepository, memberService, externalTeamService)

    @Test
    fun `팀 명이 중복될 경우 DuplicatedModelException`(){

        val createTeamRequest = TeamRequest(
            "test"
        )

        every { memberService.searchEmail(any()) } returns MEMBER

        every { teamRepository.existsByName(any()) } returns true

        shouldThrow<DuplicatedModelException> {
            teamService.createTeam("test@test.com", createTeamRequest)
        }.let { it.message shouldBe "중복 되는 팀 명 입니다 -> 중복 값 : test" }
    }

    @Test
    fun `팀 이 정상적으로 생성 확인 후 USER 권한 맴버 리더로 승격이 되는지 확인`(){

        val createTeamRequest = TeamRequest(
            "test"
        )

        every { memberService.searchEmail(any()) } returns MEMBER

        every { teamRepository.existsByName(any()) } returns false

        every { teamRepository.save(any()) } returns TEAM

        MEMBER.promotionLeader(TEAM)

        val result = teamService.createTeam("test@test.com", createTeamRequest)

        result shouldBe "팀이 생성 되었습니다 팀 명 : ${createTeamRequest.name}"
        MEMBER.getRole() shouldBe Role.LEADER
    }

    @Test
    fun `getTeamById 가 DUMMYTEAM 일 경우 DummyTeamException`(){ //MockException @CheckDummyTeam 에서 발생하는 것 같기는 한데 어떤 방식으로 대처 할 지 모르겠음

        every { validAuthentication.role(Role.LEADER, AuthenticationType.LEADER_AND_ADMIN) } returns true
        every { memberService.searchEmail(any()) } returns MEMBER

        shouldThrow<DummyTeamException> { teamService.getTeamById("test@test.com", DUMMY_TEAM) }.let { it.message shouldBe "임시 팀은 선택할 수 없습니다" }
    }



    companion object{
        private val DUMMY_TEAM = 1L
        private val TEAM = Team(
            "name",
            listOf(),
            listOf()
        )
        private val MEMBER = Member(
            "name",
            "test@test.com",
            "password",
            Role.USER,
            TEAM
        )
    }
}