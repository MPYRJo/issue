package com.yoong.myissue.exception.clazz

data class ModelNotFoundException(
    val type: String,
    val content: String
) : RuntimeException("해당 $type 은/는 존재 하지 않습 니다. 입력된 값 -> $content ")

