package com.yoong.myissue.domain.comment.controller

import com.yoong.myissue.domain.comment.dto.CreateCommentRequest
import com.yoong.myissue.domain.comment.dto.UpdateCommentRequest
import com.yoong.myissue.domain.comment.service.CommentService
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.infra.security.config.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<String> {

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(userPrincipal.email, createCommentRequest ))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<String>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(userPrincipal.email, commentId, updateCommentRequest))
    }


    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long,
    ): ResponseEntity<String>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(userPrincipal.email, commentId))
    }
}