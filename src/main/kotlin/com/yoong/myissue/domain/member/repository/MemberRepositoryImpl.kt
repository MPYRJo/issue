package com.yoong.myissue.domain.member.repository

import com.yoong.myissue.domain.member.entity.Member
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository
): MemberRepository {

    override fun existsByEmail(email: String): Boolean {
        return memberJpaRepository.existsByEmail(email)
    }

    override fun existsByNickname(nickname: String): Boolean {
        return memberJpaRepository.existsByNickname(nickname)
    }

    override fun findByEmail(email: String): Member? {
        return memberJpaRepository.findByEmail(email)
    }

    override fun save(member: Member): Member {
        return memberJpaRepository.save(member)
    }

    override fun findByIdOrNull(id: Long): Member? {
        return memberJpaRepository.findByIdOrNull(id)
    }
}