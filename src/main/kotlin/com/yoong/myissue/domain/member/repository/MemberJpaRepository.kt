package com.yoong.myissue.domain.member.repository

import com.yoong.myissue.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository: JpaRepository<Member, Long> {

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean

    fun findByEmail(email: String): Member?
}