package com.yoong.myissue.domain.member.dto


data class SignupRequest(
    val nickname : String,
    val email : String,
    val password : String,
    val password2: String
)
