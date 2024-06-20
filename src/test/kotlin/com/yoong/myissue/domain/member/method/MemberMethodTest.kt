package com.yoong.myissue.domain.member.method

import com.yoong.myissue.domain.member.common.CheckPassword
import com.yoong.myissue.exception.`class`.InvalidCredentialException
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class MemberMethodTest {

    private val checkPassword = mockk<CheckPassword>()

    @Test
    fun `CheckPassword duplicate 가 일치 하지 않을 경우 throw InvalidCredentialException`(){
        val password = "test"
        val password2 = "test2"

        every { checkPassword.duplicate(any(), any()) } throws InvalidCredentialException()

        shouldThrow<InvalidCredentialException> { checkPassword.duplicate(password, password2) }
    }
}