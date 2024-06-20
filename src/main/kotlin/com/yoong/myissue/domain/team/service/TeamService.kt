package com.yoong.myissue.domain.team.service

import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.infra.dto.UpdateResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

const val DUMMY_TEAM: Long = 1L

@Service
class TeamService(
    private val teamRepository: TeamRepository,
){

    fun createTeam(createTeamRequest: TeamRequest): String {
        //TODO("유저의 권한이 USER가 아닐 경우 NoAuthenticationException")
        //TODO("팀 이름이 중복이 될 경우 ModelNotFoundException")

        //TODO("팀 생성 후 유저를 LEADER 로 승격")
        TODO("팀 생성 후 팀 아이디를 리턴")
    }

    fun getTeamById(teamId: Long): TeamResponse {
        //TODO("유저의 권한이 LEADER나 관리자가 아닐 경우 NoAuthenticationException")
        //TODO("팀 id가 없을 경우 ModelNotFoundException")
        //TODO("LEADER 가 더미 팀을 선택할 경우 DummyTeamException")
        TODO("팀 조회 후 리턴 (조회 시 팀의 이슈 및 맴버 조회)")
    }

    fun getTeamList(): List<TeamResponse> {
        //TODO("유저의 권한이 관리자가 아닐 경우 NoAuthenticationException")
        TODO("팀 전체 조회 후 리턴 (조회 시 이슈 조회 X)")
    }

    fun updateTeam(teamId: Long, updateTeamRequest: TeamRequest): UpdateResponse {
        //TODO("유저의 권한이 LEADER나 관리자가 아닐 경우 NoAuthenticationException")
        //TODO("팀 id가 없을 경우 ModelNotFoundException")
        //TODO(" LEADER가 다른 팀을 업데이트 할 경우 NoAuthenticationException")
        TODO("변경 사항을 리턴")
    }

    fun deleteTeam(teamId: Long): String {
        //TODO("유저의 권한이 LEADER나 관리자가 아닐 경우 NoAuthenticationException")
        //TODO("팀 id가 없을 경우 ModelNotFoundException")
        //TODO(" LEADER가 다른 팀을 삭제 할 경우 NoAuthenticationException")

        TODO("Hard DELETE 진행")
    }

    fun getDummyTeam(): Team {
        return teamRepository.findByIdOrNull(DUMMY_TEAM)!!
    }
}


