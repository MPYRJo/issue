package com.yoong.myissue.domain.comment.repository

import com.yoong.myissue.domain.comment.entity.Comment

interface CommentRepository {

    fun findByIdOrNull(id: Long): Comment?

    fun save(comment: Comment): Comment

    fun delete(comment: Comment)
}