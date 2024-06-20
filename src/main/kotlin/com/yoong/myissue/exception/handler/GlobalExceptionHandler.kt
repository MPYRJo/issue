package com.yoong.myissue.exception.handler

import com.yoong.myissue.exception.`class`.DuplicatedModelException
import com.yoong.myissue.exception.`class`.InvalidCredentialException
import com.yoong.myissue.exception.`class`.ModelNotFoundException
import com.yoong.myissue.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedModelException::class)
    fun duplicatedModelException(e: DuplicatedModelException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(409, e.message?: "중복 애러"))
    }


    @ExceptionHandler(InvalidCredentialException::class)
    fun invalidCredentialException(e: InvalidCredentialException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(401, e.message?: "인증 애러"))

    }

    @ExceptionHandler(ModelNotFoundException::class)
    fun modelNotFoundException(e: InvalidCredentialException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(404, e.message?: "존재 하지 않음"))

    }
}