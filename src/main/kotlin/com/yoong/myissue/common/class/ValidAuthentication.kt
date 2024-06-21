package com.yoong.myissue.common.`class`

import com.yoong.myissue.common.enum.AuthenticationType
import com.yoong.myissue.domain.comment.entity.Comment
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.entity.Member
import org.springframework.stereotype.Component

@Component
class ValidAuthentication {


    fun role(role: Role, authenticationType: AuthenticationType):Boolean {

        when(authenticationType.name){
            "FULL_ACCESS" -> return role == Role.ADMIN
            "LEADER" -> return role == Role.LEADER
            "LEADER_AND_ADMIN" -> return role != Role.USER
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