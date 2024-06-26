package com.yoong.myissue.domain.team.service

import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse


interface TeamService {

    fun createTeam(createTeamRequest: TeamRequest, email: String): String

    fun getTeamById(teamId: Long, email: String): TeamResponse

    fun getTeamList(email: String): List<TeamResponse>

    fun updateTeam(teamId: Long, email: String, teamRequest: TeamRequest): String

    fun deleteTeam(teamId: Long, email: String): String

    fun inviteTeam(memberId: Long, email: String): String

    fun inviteMemberByAdmin(memberId: Long, email: String, teamId: Long): String

    fun firedMember(memberId: Long, email: String): String?
}