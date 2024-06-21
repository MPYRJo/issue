package com.yoong.myissue.domain.issue.dto

import com.yoong.myissue.domain.comment.dto.CommentResponse
import com.yoong.myissue.domain.comment.entity.Comment
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.enum.Priority
import com.yoong.myissue.domain.issue.enum.WorkingStatus
import com.yoong.myissue.domain.member.dto.MemberResponse
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.team.dto.TeamResponse
import java.time.LocalDateTime

data class IssueResponse (
    var id: Long,
    var title: String,
    var description: String?,
    var priority: Priority,
    var workingStatus: WorkingStatus,
    val nickname: String,
    val teamName : String,
    val createdAt: LocalDateTime,
    val comment: List<CommentResponse>
)
