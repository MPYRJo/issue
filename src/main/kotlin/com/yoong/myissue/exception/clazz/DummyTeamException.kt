package com.yoong.myissue.exception.clazz

data class DummyTeamException(
    private val msg: String?,
):RuntimeException(msg ?: "임시 팀은 선택할 수 없습니다")