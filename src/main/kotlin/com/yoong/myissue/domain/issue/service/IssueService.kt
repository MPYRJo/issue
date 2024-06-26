package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IssueService {

    fun createIssue(issueCreateRequest: IssueCreateRequest, email: String): String

    fun getIssue(issueId: Long): IssueResponse

    fun getIssueList(
        searchIssueListRequest: SearchIssueListRequest,
        email: String,
        pageable: Pageable
    ): Page<IssueResponse>

    fun updateIssue(issueId: Long, issueUpdateRequest: IssueUpdateRequest): String

    fun deleteIssue(issueId: Long): String
}