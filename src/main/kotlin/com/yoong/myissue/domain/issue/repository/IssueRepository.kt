package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.team.entity.Team
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface IssueRepository{

    fun findAllIssueList(topic: String, content: String, asc: Boolean, orderBy: String, team: Team, pageable: Pageable): Page<Issue>

    fun save(issue: Issue): Issue

    fun findByIdOrNull(id: Long): Issue?

    fun delete(issue: Issue)

    fun deletedIssue()

    fun findAllDeleted(pageable: Pageable): Page<Issue>
}
