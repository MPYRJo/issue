package com.yoong.myissue.domain.comment.dto

import com.yoong.myissue.domain.comment.enum.CheckingStatus
import com.yoong.myissue.domain.issue.dto.IssueResponse
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val checkingStatus: CheckingStatus,
    val member: MemberResponse,
    val issueId: IssueResponse,
    val createdAt: LocalDateTime,
)
