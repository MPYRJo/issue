package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.member.dto.LoginRequest
import com.yoong.myissue.domain.member.dto.LoginResponse
import com.yoong.myissue.domain.member.dto.SignupRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class MemberService {


    fun signup(signupRequest: SignupRequest): String {
        //TODO("email 이 중복이 된다면 duplicatedModelException")
        //TODO("nickName 이 중복이 된다면 duplicatedModelException")
        //TODO("첫번째 비밀 번호와 2번째 비밀번호가 일치 하지 않으면 duplicatedModelException")
        TODO("회원 가입 완료 시에 회원 가입 완료 문구 보여줌")
    }

    @Transactional
    fun login(loginRequest: LoginRequest): LoginResponse {
        //TODO("email 이 없다면 ModelNotFoundException")
        //TODO("비밀번호가 일치 하지 않다면 InvalidCredentialException")
        TODO("로그인 완료 시 이메일과 Access Token 을 리턴")
    }

}
