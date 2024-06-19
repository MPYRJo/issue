package com.yoong.myissue.domain.comment.dto

import com.yoong.myissue.domain.comment.enum.CheckingStatus

data class UpdateCommentRequest(
    var content: String,
    var checkingStatus : CheckingStatus
)
