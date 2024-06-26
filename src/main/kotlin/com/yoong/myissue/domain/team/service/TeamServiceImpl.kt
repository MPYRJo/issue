package com.yoong.myissue.domain.team.service

import com.yoong.myissue.common.annotationGather.*
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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class TeamServiceImpl(
    private val teamRepository: TeamRepository,
    private val memberService: ExternalMemberService,
    private val externalTeamService: ExternalTeamService
): TeamService {

    @CheckAuthentication(authenticationType = AuthenticationType.USER)
    override fun createTeam(createTeamRequest: TeamRequest, email: String): String {

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
    @Transactional(readOnly = true)
    override fun getTeamById(teamId: Long, email: String): TeamResponse {

        return teamRepository.findByIdOrNull(teamId)?.toTeamResponse() ?: throw ModelNotFoundException("id", teamId.toString())
    }

    @CheckAuthentication(authenticationType = AuthenticationType.ADMIN)
    @Transactional(readOnly = true)
    override fun getTeamList(email: String): List<TeamResponse> {

        return teamRepository.findAll().map { it.toTeamResponse() }
    }

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER_AND_ADMIN)
    @LeaderChoosesOtherTeam
    @CheckDummyTeam
    override fun updateTeam(teamId: Long, email: String, teamRequest: TeamRequest): String {

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        team.update(teamRequest)

        teamRepository.save(team)

        return "팀 명 변경이 완료 되었습니다 변경된 팀 명 : ${teamRequest.name}"
    }

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER_AND_ADMIN)
    @LeaderChoosesOtherTeam
    @CheckDummyTeam
    override fun deleteTeam(teamId: Long, email: String): String {

        val team = teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("id", teamId.toString())

        teamRepository.delete(team)

        return "삭제가 완료 되었습니다"
    }

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER)
    @CheckUser
    @CheckMine
    override fun inviteTeam(memberId: Long, email: String): String {

        val member = memberService.searchId(memberId)
        val leader = memberService.searchEmail(email)

        when(member.getTeam().getId()){
            DUMMY_TEAM -> member.updateTeam(leader.getTeam())
            leader.getTeam().getId() -> throw IllegalArgumentException("리더와 동일한 팀 소속 입니다")
            else -> throw IllegalArgumentException("다른 팀 소속 입니다")
        }

        return "새로운 팀 원이 등록 되었습니다"
    }

    @CheckAuthentication(authenticationType = AuthenticationType.ADMIN)
    @CheckUser
    @CheckMine
    override fun inviteMemberByAdmin(memberId: Long, email: String, teamId: Long): String {

        val member = memberService.searchId(memberId)

        val team = externalTeamService.getTeamById(teamId)

        if (teamId == DUMMY_TEAM) throw DummyTeamException()

        when(member.getTeam()){
            team -> throw IllegalArgumentException("이미 현재 팀에 소속 되어 있습니다")
            else -> member.updateTeam(team)
        }

        return "새로운 팀 원이 ${team.getTeamName()} 팀에 등록 되었습니다"
    }

    @CheckAuthentication(authenticationType = AuthenticationType.LEADER_AND_ADMIN)
    @CheckUser
    @CheckMine
    override fun firedMember(memberId: Long, email: String): String? {

        val dummyTeam = externalTeamService.getDummyTeam()
        val member = memberService.searchId(memberId)
        val leader = memberService.searchEmail(email)

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


