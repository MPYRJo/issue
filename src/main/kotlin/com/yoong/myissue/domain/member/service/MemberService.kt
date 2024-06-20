package com.yoong.myissue.domain.member.service

import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.common.PasswordManagement
import com.yoong.myissue.domain.member.dto.LoginRequest
import com.yoong.myissue.domain.member.dto.LoginResponse
import com.yoong.myissue.domain.member.dto.SignupRequest
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.repository.MemberRepository
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.service.TeamService
import com.yoong.myissue.exception.`class`.DuplicatedModelException
import com.yoong.myissue.exception.`class`.InvalidCredentialException
import com.yoong.myissue.infra.security.jwt.JwtPlugin
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val teamService: TeamService,
    private val passwordManagement: PasswordManagement,
    private val jwtPlugin: JwtPlugin
){

    @Transactional
    fun signup(signupRequest: SignupRequest): String {

        if(memberRepository.existsByEmail(signupRequest.email)) throw DuplicatedModelException( "이메일" ,signupRequest.email)

        if(memberRepository.existsByNickname(signupRequest.nickname)) throw DuplicatedModelException( "닉네임" ,signupRequest.nickname)

        passwordManagement.isSame(signupRequest.password, signupRequest.password2)

        val team: Team = teamService.getDummyTeam()

        memberRepository.save(
            Member(
                nickname = signupRequest.nickname,
                email = signupRequest.email,
                password = passwordManagement.encode(signupRequest.password),
                role = Role.USER,
                team = team
            )
        )

        return "회원 가입이 완료 되었습니다!!"
    }


    @Transactional
    fun login(loginRequest: LoginRequest): LoginResponse {

       val member = memberRepository.findByEmail(loginRequest.email) ?: throw DuplicatedModelException("이메일", loginRequest.email)

        if (passwordManagement.valid(member.password, loginRequest.password)) throw InvalidCredentialException()

        return LoginResponse(
            email = member.email,
            accessToken = jwtPlugin.generateAccessToken(member.id!!.toString(), member.email, member.role.name)
        )
    }


}
