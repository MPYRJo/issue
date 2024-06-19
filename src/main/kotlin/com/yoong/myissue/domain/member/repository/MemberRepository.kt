package com.yoong.myissue.domain.member.repository

import com.yoong.myissue.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
}