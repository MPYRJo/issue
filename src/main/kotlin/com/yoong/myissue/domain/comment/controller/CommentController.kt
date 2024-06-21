package com.yoong.myissue.domain.comment.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.yoong.myissue.domain.comment.dto.*
import com.yoong.myissue.domain.comment.service.CommentService
import com.yoong.myissue.infra.dto.UpdateResponse
import com.yoong.myissue.infra.security.config.UserPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal


@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(createCommentRequest, userPrincipal.email))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, updateCommentRequest))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentId))
    }
}