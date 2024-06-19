package com.yoong.myissue.domain.issue.dto

data class IssueUpdateResponse(
    val column : String,
    val oldData : String,
    val newData : String,
)
