package com.yoong.myissue.domain.team.service

import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse


interface TeamService {

    // Team 생성
    fun createTeam(createTeamRequest: TeamRequest, email: String): String

    // Team 아이디 로 단건 조회
    fun getTeamById(teamId: Long, email: String): TeamResponse

    // Team 전체 조회
    fun getTeamList(email: String): List<TeamResponse>

    // Team 업데이트
    fun updateTeam(teamId: Long, email: String, teamRequest: TeamRequest): String

    // 팀 해체
    fun deleteTeam(teamId: Long, email: String): String

    // 팀원 초대
    fun inviteTeam(memberId: Long, email: String): String

    // (관리자 전용) 팀원 초대
    fun inviteMemberByAdmin(memberId: Long, email: String, teamId: Long): String

    // 팀원 방출
    fun firedMember(memberId: Long, email: String): String?
}