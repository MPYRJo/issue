package com.yoong.myissue.domain.member.dto

import com.yoong.myissue.domain.issue.enumGather.Role

data class MemberResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val role: Role,
)
