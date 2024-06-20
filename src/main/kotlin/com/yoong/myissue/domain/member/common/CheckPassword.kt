package com.yoong.myissue.domain.member.common

import com.yoong.myissue.exception.`class`.InvalidCredentialException
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class CheckPassword {

    fun duplicate(password: String, password2: String) {
        if(password != password2) throw InvalidCredentialException()
    }
}