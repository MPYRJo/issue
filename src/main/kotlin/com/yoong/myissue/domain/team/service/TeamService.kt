package com.yoong.myissue.domain.team.service

import com.yoong.myissue.common.annotation.CheckAuthentication
import com.yoong.myissue.common.annotation.CheckDummyTeam
import com.yoong.myissue.common.enum.AuthenticationType
import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.dto.TeamAdminInviteRequest
import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.exception.clazz.DuplicatedModelException
import com.yoong.myissue.exception.clazz.ModelNotFoundException
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

    @CheckAuthentication(authenticationType = AuthenticationType.ADMIN)
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

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER)
    fun inviteTeam(userId: Long, email: String): String {

        val member = memberService.searchId(userId)
        val leader = memberService.searchEmail(email)

        when(member.getTeam().getId()){
            DUMMY_TEAM -> member.updateTeam(leader.getTeam())
            leader.getTeam().getId() -> throw IllegalArgumentException("리더와 동일한 팀 소속 입니다")
            else -> throw IllegalArgumentException("다른 팀 소속 입니다")
        }

        return "새로운 팀 원이 등록 되었습니다"
    }

    fun inviteMemberByAdmin(teamAdminInviteRequest: TeamAdminInviteRequest, email: String): String? {
        //TODO("권한이 관리자가 아닐 경우 NoAuthenticationException")
        //TODO("팀원의 아이디가 없을 경우 ModelNotFoundException")
        //TODO("팀 id가 DUMMY_TEAM 일 경우 DummyTeamException")
        TODO("팀원의 팀을 가져온 팀 정보의 팀과 일치 시킨 후에 정보를 리턴")
    }

    fun firedMember(memberId: Long, email: String): String? {
        //TODO("권한이 리더나 관리자가 아닐 경우 NoAuthenticationException")
        //TODO("팀원의 아이디가 없을 경우 ModelNotFoundException")
        TODO("팀원의 팀을 가져온 DUMMY_TEAM 팀과 일치 시킨 후에 정보를 리턴")
    }


}


