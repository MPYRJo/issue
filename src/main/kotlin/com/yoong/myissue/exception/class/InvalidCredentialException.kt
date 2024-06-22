package com.yoong.myissue.exception.`class`

data class InvalidCredentialException(
    private val content: String,
): RuntimeException(content)
