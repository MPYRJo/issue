package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.domain.issue.entity.Issue
import org.springframework.data.jpa.repository.JpaRepository

interface IssueJpaRepository: JpaRepository<Issue, Long> {

//    fun findByIdOrNull(id: Long): Issue?
}