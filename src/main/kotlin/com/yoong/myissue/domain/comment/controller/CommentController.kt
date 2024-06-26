package com.yoong.myissue.domain.comment.controller

import com.yoong.myissue.common.annotationGather.FailedLogin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.yoong.myissue.domain.comment.dto.*
import com.yoong.myissue.domain.comment.service.CommentService
import com.yoong.myissue.exception.clazz.NoAuthenticationException
import com.yoong.myissue.infra.security.config.UserPrincipal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal


@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {

    @FailedLogin
    @PostMapping
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(userPrincipal!!.email, createCommentRequest ))
    }

    @FailedLogin
    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(userPrincipal!!.email, commentId, updateCommentRequest))
    }

    @FailedLogin
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(userPrincipal!!.email, commentId))
    }
}