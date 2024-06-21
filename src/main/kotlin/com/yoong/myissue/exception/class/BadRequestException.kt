package com.yoong.myissue.exception.`class`

data class BadRequestException(
    val msg: String,
):RuntimeException(msg)