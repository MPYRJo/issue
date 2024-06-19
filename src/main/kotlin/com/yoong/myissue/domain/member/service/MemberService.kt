package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.member.dto.SignupRequest
import org.springframework.stereotype.Service


@Service
class MemberService {


    fun signup(signupRequest: SignupRequest): String {
        //TODO("email 이 중복이 된다면 duplicatedModelException")
        //TODO("nickName 이 중복이 된다면 duplicatedModelException")
        //TODO("첫번째 비밀 번호와 2번째 비밀번호가 일치 하지 않으면 duplicatedModelException")
        TODO("회원 가입 완료 시에 회원 가입 완료 문구 보여줌")
    }

}
