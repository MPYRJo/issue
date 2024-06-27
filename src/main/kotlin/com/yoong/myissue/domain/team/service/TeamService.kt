package com.yoong.myissue.domain.team.service

import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse


interface TeamService {

    // Team 생성
    fun createTeam(email: String, createTeamRequest: TeamRequest): String

    // Team 아이디 로 단건 조회
    fun getTeamById(email: String, teamId: Long): TeamResponse

    // Team 전체 조회
    fun getTeamList(email: String): List<TeamResponse>

    // Team 업데이트
    fun updateTeam( email: String, teamId: Long, teamRequest: TeamRequest): String

    // 팀 해체
    fun deleteTeam(email: String, teamId: Long): String

    // 팀원 초대
    fun inviteTeam(email: String, memberId: Long): String

    // (관리자 전용) 팀원 초대
    fun inviteMemberByAdmin(email: String, memberId: Long, teamId: Long): String

    // 팀원 방출
    fun firedMember(email: String, memberId: Long): String?
}