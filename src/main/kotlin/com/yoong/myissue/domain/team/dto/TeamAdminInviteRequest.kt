package com.yoong.myissue.domain.team.dto

data class TeamAdminInviteRequest(
    val teamId: Long,
    val memberId: Long
)