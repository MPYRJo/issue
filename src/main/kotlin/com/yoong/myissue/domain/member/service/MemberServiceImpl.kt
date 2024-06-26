package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.common.clazz.PasswordManagement
import com.yoong.myissue.domain.member.dto.LoginRequest
import com.yoong.myissue.domain.member.dto.LoginResponse
import com.yoong.myissue.domain.member.dto.SignupRequest
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.repository.MemberRepository
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.exception.clazz.DuplicatedModelException
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.infra.security.jwt.JwtPlugin
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val teamService:ExternalTeamService,
    private val passwordManagement: PasswordManagement,
    private val jwtPlugin: JwtPlugin
):MemberService{

    @Transactional
    override fun signup(signupRequest: SignupRequest): String {

        if(memberRepository.existsByEmail(signupRequest.email)) throw DuplicatedModelException( "이메일" ,signupRequest.email)

        if(memberRepository.existsByNickname(signupRequest.nickname)) throw DuplicatedModelException( "닉네임" ,signupRequest.nickname)

        passwordManagement.isSame(signupRequest.password, signupRequest.password2)

        memberRepository.save(
            Member(
                nickname = signupRequest.nickname,
                email = signupRequest.email,
                password = passwordManagement.encode(signupRequest.password),
                role = Role.USER,
                team = teamService.getDummyTeam()
            )
        )

        return "회원 가입이 완료 되었습니다!!"
    }


    @Transactional
    override fun login(loginRequest: LoginRequest): LoginResponse {

       val member = memberRepository.findByEmail(loginRequest.email)?: throw InvalidCredentialException("이메일이 일치하지 않습니다")

       if (member.validPassword(passwordManagement,loginRequest.password)) throw InvalidCredentialException("비밀 번호가 일치하지 않습니다")

        return LoginResponse(
            email = loginRequest.email,
            accessToken = member.getAccessToken(jwtPlugin)
        )
    }



}
