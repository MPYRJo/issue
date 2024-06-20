package com.yoong.myissue.domain.issue.dto

import com.yoong.myissue.domain.issue.enum.Priority
import com.yoong.myissue.domain.issue.enum.WorkingStatus
import com.yoong.myissue.domain.member.dto.MemberResponse
import com.yoong.myissue.domain.team.dto.TeamResponse
import java.time.LocalDateTime

data class IssueResponse (
    var id: Long,
    var title: String,
    var description: String?,
    var priority: Priority,
    var workingStatus: WorkingStatus,
    val member: MemberResponse,
    val team : TeamResponse,
    val createdAt: LocalDateTime,
)
