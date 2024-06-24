package com.yoong.myissue.domain.comment.dto

import com.yoong.myissue.domain.comment.enumGather.CheckingStatus

data class CreateCommentRequest(
    val issueId: Long,
    var content: String,
    var checkingStatus : CheckingStatus
)
