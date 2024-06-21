package com.yoong.myissue.domain.member.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Pattern



data class SignupRequest(

    @field:NotBlank
    @field:Size(min = 3, max = 10)
    @field:Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 알파벳 대소문자, 숫자, 한글만 포함할 수 있습니다")
    val nickname : String,

    @field:NotBlank
    @field:Email
    val email : String,

    @field:NotBlank
    val password : String,
    val password2: String
)
