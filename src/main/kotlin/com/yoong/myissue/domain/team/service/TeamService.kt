package com.yoong.myissue.domain.team.service

import com.yoong.myissue.common.annotation.CheckAuthentication
import com.yoong.myissue.common.annotation.CheckDummyTeam
import com.yoong.myissue.common.enum.AuthenticationType
import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.exception.`class`.DuplicatedModelException
import com.yoong.myissue.exception.`class`.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class TeamService(
    private val teamRepository: TeamRepository,
    private val memberService: ExternalMemberService,
){

    @CheckAuthentication(authenticationType = AuthenticationType.USER)
    fun createTeam(createTeamRequest: TeamRequest, email: String): String {

        val member = memberService.searchEmail(email)

        if(teamRepository.existsByName(createTeamRequest.name)) throw DuplicatedModelException("팀 명", createTeamRequest.name)

        val team = teamRepository.save(Team(
            name = createTeamRequest.name,
        ))

        team.let { member.promotionLeader(it) }

        return "팀이 생성 되었습니다 팀 명 : ${createTeamRequest.name}"
    }

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER_AND_ADMIN)
    @CheckDummyTeam
    fun getTeamById(teamId: Long, email: String): TeamResponse {

        return teamRepository.findByIdOrNull(teamId)?.toTeamResponse() ?: throw ModelNotFoundException("id", teamId.toString())
    }

    @CheckAuthentication(authenticationType = AuthenticationType.FULL_ACCESS)
    fun getTeamList(email: String): List<TeamResponse> {

        return teamRepository.findAll().map { it.toTeamResponse() }
    }

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER_AND_ADMIN)
    @CheckDummyTeam
    fun updateTeam(teamId: Long, teamRequest: TeamRequest, email: String): String {

        val member = memberService.searchEmail(email)

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        if(member.getRole() == Role.LEADER && team != member.getTeam()) throw IllegalArgumentException()

        team.update(teamRequest)

        teamRepository.save(team)

        return "팀 명 변경이 완료 되었습니다 변경된 팀 명 : ${teamRequest.name}"
    }

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER_AND_ADMIN)
    @CheckDummyTeam
    fun deleteTeam(teamId: Long, email: String): String {

        val member = memberService.searchEmail(email)

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        if(member.getRole() == Role.LEADER && team != member.getTeam()) throw IllegalArgumentException()

        teamRepository.delete(team)

        return "삭제가 완료 되었습니다"
    }


}


