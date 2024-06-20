package com.yoong.myissue.exception.`class`

data class DuplicatedModelException(
    val title: String,
    val content: String
) : RuntimeException("중복 되는 ${title}이 있습니다 -> 중복 값 : $content")
