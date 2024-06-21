package com.yoong.myissue.domain.comment.dto

import com.yoong.myissue.domain.comment.enum.CheckingStatus
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val checkingStatus: CheckingStatus,
    val nickname: String,
    val createdAt: LocalDateTime,
)
