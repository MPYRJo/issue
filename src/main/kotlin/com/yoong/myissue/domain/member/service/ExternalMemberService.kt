package com.yoong.myissue.domain.member.service

import com.yoong.myissue.common.`class`.ValidAuthentication
import com.yoong.myissue.common.enum.AuthenticationType
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.repository.MemberRepository
import com.yoong.myissue.exception.`class`.ModelNotFoundException
import com.yoong.myissue.exception.`class`.NoAuthenticationException
import jakarta.validation.Valid
import org.springframework.stereotype.Service

@Service
class ExternalMemberService(
    private val memberRepository: MemberRepository,
) {

    fun searchEmail(email: String): Member {

        return memberRepository.findByEmail(email) ?: throw ModelNotFoundException("맴버", email)
    }
}