package com.yoong.myissue.exception.handler

import com.yoong.myissue.exception.clazz.*
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
    fun modelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(404, e.message?: "존재 하지 않음"))

    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(404, e.message?: "잘못된 값을 입력 하였습니다"))

    }

    @ExceptionHandler(NoAuthenticationException::class)
    fun noAuthenticationException(e: NoAuthenticationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse(403, e.message?: "권한 없음"))

    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(e: BadRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(400, e.message?: "잘못된 접근"))

    }

    @ExceptionHandler(DummyTeamException::class)
    fun dummyTeamException(e: DummyTeamException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(400, e.message?: "더미팀 선택 불가"))

    }
}