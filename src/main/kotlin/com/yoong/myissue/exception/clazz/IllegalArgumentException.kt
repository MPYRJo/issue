package com.yoong.myissue.exception.clazz

data class IllegalArgumentException(
    val msg : String,
):RuntimeException(msg)