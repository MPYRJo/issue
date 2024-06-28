package com.yoong.myissue.domain.issue.dto

import com.yoong.myissue.domain.comment.dto.CommentResponse
import com.yoong.myissue.domain.issue.enumGather.Priority
import com.yoong.myissue.domain.issue.enumGather.WorkingStatus
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
    val comment: List<CommentResponse>,
    val imageUrl : String?,
)
