package com.yoong.myissue.domain.member.common

import com.yoong.myissue.exception.`class`.InvalidCredentialException
import com.yoong.myissue.infra.security.jwt.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordManagement(
    private val passwordEncoder: PasswordEncoder,
    ) {

    fun isSame(password: String, password2: String) {
        if(password != password2) throw InvalidCredentialException("비밀번호")
    }

    fun encode(password: String): String {
        return passwordEncoder.bCryptPasswordEncoder().encode(password)
    }

    fun valid(password: String, typingPassword: String): Boolean {
        return !passwordEncoder.bCryptPasswordEncoder().matches(typingPassword, password)
    }

}