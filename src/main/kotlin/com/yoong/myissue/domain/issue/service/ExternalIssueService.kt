package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.repository.IssueJpaRepository
import com.yoong.myissue.domain.issue.repository.IssueRepository
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.springframework.stereotype.Service

@Service
class ExternalIssueService(
    private val issueRepository: IssueRepository
) {

    fun getIssue(issueId: Long): Issue {
        return issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())
    }
}