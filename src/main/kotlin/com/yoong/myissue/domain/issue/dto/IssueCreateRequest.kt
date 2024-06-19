package com.yoong.myissue.domain.issue.dto

import com.yoong.myissue.domain.issue.enum.Priority
import com.yoong.myissue.domain.issue.enum.WorkingStatus

data class IssueCreateRequest(
    var title: String,
    var description: String?,
    var priority: Priority,
    var workingStatus: WorkingStatus,
)

