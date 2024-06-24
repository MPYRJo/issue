package com.yoong.myissue.domain.issue.dto

import com.yoong.myissue.domain.issue.enumGather.Priority
import com.yoong.myissue.domain.issue.enumGather.WorkingStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class IssueCreateRequest(
    @field:NotBlank(message = "제목은 빈 칸일 수 없습니다")
    @field:Size(min = 3, max = 100, message = "제목은 3자 부터 100자 까지 입력이 가능 합니다")
    var title: String,

    @field:Size(max = 5000, message = "내용은 5000자를 넘을 수 없습니다")
    var description: String?,

    var priority: Priority,
    var workingStatus: WorkingStatus,
)

