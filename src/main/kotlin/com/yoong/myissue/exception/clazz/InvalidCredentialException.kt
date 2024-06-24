package com.yoong.myissue.exception.clazz

data class InvalidCredentialException(
    private val content: String,
): RuntimeException(content)
