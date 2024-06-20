package com.yoong.myissue.domain.team.dto

import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.member.dto.MemberResponse
import com.yoong.myissue.domain.team.entity.Team

data class TeamResponse(
    val id: Long,
    val name: String,
    val issue: List<IssueResponse>,
    val member: List<MemberResponse>
){
    companion object{
        fun from(team: Team): TeamResponse {
            return TeamResponse(
                id = team.id!!,
                name = team.name,
                issue = team.issues.map { IssueResponse.from(it) },
                member = team.members.map { MemberResponse.from(it) }
            )
        }
    }
}
