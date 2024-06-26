package com.yoong.myissue.domain.member.repository

import com.yoong.myissue.domain.member.entity.Member

interface MemberRepository {

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean

    fun findByEmail(email: String): Member?

    fun save(member: Member): Member

    fun findByIdOrNull(id: Long): Member?
}