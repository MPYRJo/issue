package com.yoong.myissue.common.`class`

import com.yoong.myissue.common.enum.AuthenticationType
import com.yoong.myissue.domain.comment.entity.Comment
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.member.service.MemberService
import com.yoong.myissue.exception.`class`.NoAuthenticationException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Component
class ValidAuthentication(
){

    fun role(role: Role, authenticationType: AuthenticationType):Boolean {


        return when(authenticationType.name){
            "USER" -> if(role == Role.USER) true else throw NoAuthenticationException()
            "FULL_ACCESS" -> if(role == Role.ADMIN) true else throw NoAuthenticationException()
            "LEADER" -> if(role == Role.LEADER) true else throw NoAuthenticationException()
            "LEADER_AND_ADMIN" -> if(role != Role.USER) true else throw NoAuthenticationException()
            else -> throw IllegalArgumentException("해당 규칙은 존재하지 않습니다")
        }

    }

    fun isSame(member: Member, comment: Comment):Boolean {
        return member.getId() == comment.getMember()
    }

    fun isSame(member: Member, issue: Issue):Boolean {
        return member.getId() == issue.getMember()
    }

}