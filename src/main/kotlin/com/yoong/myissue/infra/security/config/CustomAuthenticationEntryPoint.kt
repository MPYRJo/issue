package com.yoong.myissue.infra.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.yoong.myissue.exception.dto.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {

        response.status = HttpServletResponse.SC_UNAUTHORIZED

        response.contentType = MediaType.APPLICATION_JSON_VALUE

        response.characterEncoding = "UTF-8"

        val objectMapper = ObjectMapper()
        val jsonString = objectMapper.writeValueAsString(ErrorResponse(401, "JWT 인증에 실패 했습니다"))
        response.writer.write(jsonString)
    }
}