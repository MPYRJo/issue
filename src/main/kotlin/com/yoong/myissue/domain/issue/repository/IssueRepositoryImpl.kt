package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class IssueRepositoryImpl:QueryDslSupport(), CustomIssueRepository {
}