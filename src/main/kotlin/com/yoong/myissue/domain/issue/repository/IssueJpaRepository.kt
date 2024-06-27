package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.domain.issue.entity.Issue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface IssueJpaRepository: JpaRepository<Issue, Long> {

//    fun findByIdOrNull(id: Long): Issue?

    @Modifying
    @Query("delete from Issue i where i.deletedAt = current date + 90")
    fun deletedIssue()
}