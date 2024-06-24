package com.yoong.myissue.domain.issue.dto

data class SearchIssueListRequest(
    val topic : String = "title",
    val content: String = "",
    val asc: Boolean = true,
    val orderBy: String = "createdAt",
)
