package com.yoong.myissue.domain.member.dto

data class LoginResponse(
    val email: String,
    val accessToken: String,
)
