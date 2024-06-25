package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.domain.issue.entity.Issue
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomIssueRepository{
    fun findAll(topic: String, content: String, asc: Boolean, orderBy: String, pageable: Pageable): Page<Issue>
}
