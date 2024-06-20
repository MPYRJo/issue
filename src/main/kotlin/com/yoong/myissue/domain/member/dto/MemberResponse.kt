package com.yoong.myissue.domain.member.dto

import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.entity.Member

data class MemberResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val role: Role,
){
    companion object{
        fun from(member: Member): MemberResponse{
            return MemberResponse(
                id = member.id!!,
                email = member.email,
                nickname = member.nickname,
                role = member.role
            )
        }
    }
}
