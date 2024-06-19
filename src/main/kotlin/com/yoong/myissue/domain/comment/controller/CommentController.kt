package com.yoong.myissue.domain.comment.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.yoong.myissue.domain.comment.dto.*
import com.yoong.myissue.infra.dto.UpdateResponse




@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(createCommentRequest))
    }

    @GetMapping
    fun getCommentList(): ResponseEntity<List<CommentResponse>> {

        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentList())
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<UpdateResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, updateCommentRequest))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentId))
    }
}