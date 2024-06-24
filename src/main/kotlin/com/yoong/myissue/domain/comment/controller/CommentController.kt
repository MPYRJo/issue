package com.yoong.myissue.domain.comment.controller

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

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_LEADER')")
    @PostMapping
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<String> {

        if(userPrincipal == null) throw NoAuthenticationException()

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(createCommentRequest, userPrincipal.email))
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_LEADER')")
    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<String>{

        if(userPrincipal == null) throw NoAuthenticationException()

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, updateCommentRequest, userPrincipal.email))
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_LEADER')")
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long,
    ): ResponseEntity<String>{

        if(userPrincipal == null) throw NoAuthenticationException()

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentId, userPrincipal.email))
    }
}