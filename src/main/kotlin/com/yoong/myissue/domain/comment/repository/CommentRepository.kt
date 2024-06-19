package com.yoong.myissue.domain.comment.repository

import com.yoong.myissue.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}