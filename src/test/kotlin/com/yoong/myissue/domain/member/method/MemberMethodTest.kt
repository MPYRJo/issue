package com.yoong.myissue.domain.member.method

import com.yoong.myissue.common.clazz.PasswordManagement
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class MemberMethodTest {

    private val passwordManagement = mockk<PasswordManagement>()

    @Test
    fun `CheckPassword duplicate 가 일치 하지 않을 경우 throw InvalidCredentialException`(){
        val password = "test"
        val password2 = "test2"

        every { passwordManagement.isSame(any(), any()) } throws InvalidCredentialException()

        shouldThrow<InvalidCredentialException> { passwordManagement.isSame(password, password2) }
    }
}