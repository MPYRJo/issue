package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.domain.issue.entity.Issue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface IssueJpaRepository: JpaRepository<Issue, Long> {

    fun findByIdAndDeletedAtIsNull(id: Long): Issue?

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM issue WHERE deleted_at <= NOW() - INTERVAL '90' day ", nativeQuery = true)
    fun deletedIssue()
}