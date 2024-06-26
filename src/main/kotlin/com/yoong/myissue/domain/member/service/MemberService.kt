package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.member.dto.LoginRequest
import com.yoong.myissue.domain.member.dto.LoginResponse
import com.yoong.myissue.domain.member.dto.SignupRequest

interface MemberService {

    // 회원 가입 처리
    fun signup(signupRequest: SignupRequest): String

    // 로그인 처리
    fun login(loginRequest: LoginRequest): LoginResponse

}