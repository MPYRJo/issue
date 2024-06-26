package com.yoong.myissue.domain.comment.repository

import com.yoong.myissue.domain.comment.entity.Comment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentRepositoryImpl(
    private val commentJpaRepository: CommentJpaRepository
): CommentRepository {

    override fun findByIdOrNull(id: Long): Comment? {
        return commentJpaRepository.findByIdOrNull(id)
    }

    override fun save(comment: Comment): Comment {
        return commentJpaRepository.save(comment)
    }

    override fun delete(comment: Comment) {
        commentJpaRepository.delete(comment)
    }
}