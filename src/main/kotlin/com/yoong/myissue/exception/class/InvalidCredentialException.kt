package com.yoong.myissue.exception.`class`

data class InvalidCredentialException(
    private val content: String,
): RuntimeException("$content 이/가 일치 하지 않습니다")
