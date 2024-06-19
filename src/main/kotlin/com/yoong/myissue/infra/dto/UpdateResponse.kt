package com.yoong.myissue.infra.dto

data class UpdateResponse(
    val column : String,
    val oldData : String,
    val newData : String,
)
