package com.yoong.myissue.exception.clazz

data class BadRequestException(
    val msg: String,
):RuntimeException(msg)