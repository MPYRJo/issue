package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.dto.LoginRequest
import com.yoong.myissue.domain.member.dto.LoginResponse
import com.yoong.myissue.domain.member.dto.SignupRequest
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.repository.MemberRepository
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.service.TeamService
import com.yoong.myissue.exception.`class`.DuplicatedModelException
import com.yoong.myissue.exception.`class`.InvalidCredentialException
import com.yoong.myissue.infra.security.jwt.PasswordEncoder
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val teamService: TeamService,
    private val passwordEncoder: PasswordEncoder
){

    @Transactional
    fun signup(signupRequest: SignupRequest): String {

        if(memberRepository.existsByEmail(signupRequest.email)) throw DuplicatedModelException( "이메일" ,signupRequest.email)

        if(memberRepository.existsByNickname(signupRequest.nickname)) throw DuplicatedModelException( "닉네임" ,signupRequest.nickname)

        matchPassword(signupRequest.password, signupRequest.password2)

        val team: Team = teamService.getDummyTeam()

        memberRepository.save(
            Member(
                nickname = signupRequest.nickname,
                email = signupRequest.email,
                password = passwordEncoder.bCryptPasswordEncoder().encode(signupRequest.password),
                role = Role.USER,
                team = team
            )
        )

        return "회원 가입이 완료 되었습니다!!"
    }


    @Transactional
    fun login(loginRequest: LoginRequest): LoginResponse {
        //TODO("email 이 없다면 ModelNotFoundException")
        //TODO("비밀번호가 일치 하지 않다면 InvalidCredentialException")
        TODO("로그인 완료 시 이메일과 Access Token 을 리턴")
    }

    private fun matchPassword(password: String, password2: String) {
        if(password != password2) throw InvalidCredentialException()
    }

}
