package com.yoong.myissue.domain.issue.repository

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.entity.QIssue
import com.yoong.myissue.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class IssueRepositoryImpl:QueryDslSupport(), CustomIssueRepository {

    private val issue = QIssue.issue

    override fun findAll(topic: String, content: String, asc: Boolean, orderBy: String): List<Issue> {

        return queryFactory
            .selectFrom(issue)
            .where(
                topicToContent(topic, content)
            )
            .orderBy(getOrderBy(asc, issue, orderBy))
            .fetch()
    }

    fun topicToContent(topic: String, content: String): BooleanExpression {

        return when(topic){
            "title" -> issue.title.like("%$content%")
            "description" -> issue.description.like("%$content%")
            "nickname" -> issue.member.nickname.like("%$content%")
            else -> throw IllegalArgumentException("조건을 잘못 입력 하셨습니다")
        }

    }

    private fun getOrderBy(ascend: Boolean, path: EntityPathBase<*>, str: String): OrderSpecifier<*> {
        val pathBuilder = PathBuilder(path.type, path.metadata)


        return OrderSpecifier(
            if (ascend) Order.ASC else Order.DESC,
            pathBuilder.get(str) as Expression<Comparable<*>>,
        )
    }
}