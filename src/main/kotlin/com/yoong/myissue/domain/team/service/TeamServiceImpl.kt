package com.yoong.myissue.domain.team.service

import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.common.enumGather.AuthenticationType
import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.exception.clazz.DummyTeamException
import com.yoong.myissue.exception.clazz.DuplicatedModelException
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class TeamServiceImpl(
    private val teamRepository: TeamRepository,
    private val memberService: ExternalMemberService,
    private val externalTeamService: ExternalTeamService,
    private val validAuthentication: ValidAuthentication
){

    fun createTeam(email: String, createTeamRequest: TeamRequest): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.USER)


        if(teamRepository.existsByName(createTeamRequest.name)) throw DuplicatedModelException("팀 명", createTeamRequest.name)

        val team = teamRepository.save(Team(
            name = createTeamRequest.name,
        ))

        team.let { member.promotionLeader(it) }

        return "팀이 생성 되었습니다 팀 명 : ${createTeamRequest.name}"
    }

    @Transactional(readOnly = true)
    fun getTeamById(email: String, teamId: Long): TeamResponse {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        if (teamId == DUMMY_TEAM) throw DummyTeamException(null)

        return teamRepository.findByIdOrNull(teamId)?.toTeamResponse() ?: throw ModelNotFoundException("id", teamId.toString())
    }

    @Transactional(readOnly = true)
    fun getTeamList(email: String): List<TeamResponse> {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ADMIN)

        return teamRepository.findAll().map { it.toTeamResponse() }
    }

    fun updateTeam(email: String, teamId: Long, teamRequest: TeamRequest): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        if (teamId == DUMMY_TEAM) throw DummyTeamException(null)

        if(member.getRole() == Role.LEADER && team != member.getTeam()) throw IllegalArgumentException("본인 소속 외에 다른 팀은 선택할 수 없습니다")

        team.update(teamRequest)

        teamRepository.save(team)

        return "팀 명 변경이 완료 되었습니다 변경된 팀 명 : ${teamRequest.name}"
    }

    fun deleteTeam(email: String, teamId: Long): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        if (teamId == DUMMY_TEAM) throw DummyTeamException(null)

        if(member.getRole() == Role.LEADER && team != member.getTeam()) throw IllegalArgumentException("본인 소속 외에 다른 팀은 선택할 수 없습니다")

        teamRepository.delete(team)

        return "삭제가 완료 되었습니다"
    }

    fun inviteTeam(email: String, memberId: Long): String {

        val leader = memberService.searchEmail(email)

        validAuthentication.role(leader.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        if(leader.getRole() != Role.USER) throw IllegalArgumentException("리더 및 관리자는 팀원으로 추가 및 방출할 수 없습니다")

        val member = memberService.searchId(memberId)

        if(member == leader) throw DuplicatedModelException("맴버", "나 자신을 선택할 수 없습니다")

        when(member.getTeam().getId()){
            DUMMY_TEAM -> member.updateTeam(leader.getTeam())
            leader.getTeam().getId() -> throw IllegalArgumentException("리더와 동일한 팀 소속 입니다")
            else -> throw IllegalArgumentException("다른 팀 소속 입니다")
        }

        return "새로운 팀 원이 등록 되었습니다"
    }

    fun inviteMemberByAdmin(email: String, memberId: Long, teamId: Long): String {

        val admin = memberService.searchEmail(email)

        validAuthentication.role(admin.getRole(), AuthenticationType.ADMIN)

        val member = memberService.searchId(memberId)

        if(member.getRole() != Role.USER) throw IllegalArgumentException("리더 및 관리자는 팀원으로 추가 및 방출할 수 없습니다")

        if(member == admin) throw DuplicatedModelException("맴버", "나 자신을 선택할 수 없습니다")

        val team = externalTeamService.getTeamById(teamId)

        if (teamId == DUMMY_TEAM) throw DummyTeamException(null)

        when(member.getTeam()){
            team -> throw IllegalArgumentException("이미 현재 팀에 소속 되어 있습니다")
            else -> member.updateTeam(team)
        }

        return "새로운 팀 원이 ${team.getTeamName()} 팀에 등록 되었습니다"
    }

    fun firedMember(email: String, memberId: Long): String? {

        val leader = memberService.searchEmail(email)

        validAuthentication.role(leader.getRole(), AuthenticationType.LEADER_AND_ADMIN)

        val dummyTeam = externalTeamService.getDummyTeam()
        val member = memberService.searchId(memberId)
        if(member.getRole() != Role.USER) throw IllegalArgumentException("리더 및 관리자는 팀원으로 추가 및 방출할 수 없습니다")
        if(member == leader) throw DuplicatedModelException("맴버", "나 자신을 선택할 수 없습니다")


        if(leader.getRole() == Role.LEADER){
            when(member.getTeam()){
                leader.getTeam() -> member.updateTeam(dummyTeam)
                else -> throw IllegalArgumentException("다른 팀 소속 입니다")
            }
        }else{
            member.updateTeam(dummyTeam)
        }

        return "팀 원 그룹 탈퇴 처리 완료 했습니다"
    }

}


