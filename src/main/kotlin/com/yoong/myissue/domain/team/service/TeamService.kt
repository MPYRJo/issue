package com.yoong.myissue.domain.team.service

import com.yoong.myissue.common.`class`.ValidAuthentication
import com.yoong.myissue.common.enum.AuthenticationType
import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.exception.`class`.DummyTeamException
import com.yoong.myissue.exception.`class`.DuplicatedModelException
import com.yoong.myissue.exception.`class`.ModelNotFoundException
import com.yoong.myissue.infra.dto.UpdateResponse
import jakarta.validation.constraints.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class TeamService(
    private val teamRepository: TeamRepository,
    private val memberService: ExternalMemberService,
){

    private val validAuthentication: ValidAuthentication = ValidAuthentication()

    fun createTeam(createTeamRequest: TeamRequest, email: String): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.USER)

        if(teamRepository.existsByName(createTeamRequest.name)) throw DuplicatedModelException("팀 명", createTeamRequest.name)

        val team = teamRepository.save(Team(
            name = createTeamRequest.name,
        ))

        team.let { member.promotionLeader(it) }

        return "팀이 생성 되었습니다 팀 명 : ${createTeamRequest.name}"
    }

    fun getTeamById(teamId: Long, email: String): TeamResponse {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        if(member.getRole() == Role.LEADER && teamId == DUMMY_TEAM) throw DummyTeamException()

        return teamRepository.findByIdOrNull(teamId)?.toTeamResponse() ?: throw ModelNotFoundException("id", teamId.toString())
    }

    fun getTeamList(email: String): List<TeamResponse> {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.FULL_ACCESS)

        return teamRepository.findAll().map { it.toTeamResponse() }
    }

    fun updateTeam(teamId: Long, teamRequest: TeamRequest, email: String): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        if(teamId == DUMMY_TEAM) throw DummyTeamException()

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        if(member.getRole() == Role.LEADER && team != member.getTeam()) throw IllegalArgumentException()

        team.update(teamRequest)

        teamRepository.save(team)

        return "팀 명 변경이 완료 되었습니다 변경된 팀 명 : ${teamRequest.name}"
    }

    fun deleteTeam(teamId: Long, email: String): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        if(teamId == DUMMY_TEAM) throw DummyTeamException()

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        if(member.getRole() == Role.LEADER && team != member.getTeam()) throw IllegalArgumentException()

        teamRepository.delete(team)

        return "삭제가 완료 되었습니다"

    }
}


