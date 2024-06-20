package com.yoong.myissue.domain.team.dto

import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.member.dto.MemberResponse

data class TeamResponse(
    val id: Long,
    val name: String,
    val issue: List<IssueResponse>,
    val member: List<MemberResponse>
)
