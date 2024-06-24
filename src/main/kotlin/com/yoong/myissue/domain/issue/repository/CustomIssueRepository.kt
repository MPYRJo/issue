package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.domain.issue.entity.Issue

interface CustomIssueRepository{
    fun findAll(topic: String, content: String, asc: Boolean, orderBy: String): List<Issue>
}
