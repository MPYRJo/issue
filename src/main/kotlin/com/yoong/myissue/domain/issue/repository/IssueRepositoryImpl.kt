package com.yoong.myissue.domain.issue.repository

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.entity.QIssue
import com.yoong.myissue.domain.issue.enumGather.Priority
import com.yoong.myissue.domain.issue.enumGather.WorkingStatus
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class IssueRepositoryImpl(
    val issueJpaRepository: IssueJpaRepository,
    @PersistenceContext
    private val em: EntityManager,
): IssueRepository {

    private val issue = QIssue.issue

    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)


    override fun findAllIssueList(topic: String, content: String, asc: Boolean, orderBy: String, team: Team, pageable: Pageable): Page<Issue> {

        val query = queryFactory
            .selectFrom(issue)
            .where(
                topicToContent(topic, content),
                issue.deletedAt.isNull(),
                issue.team.eq(team),
            ).orderBy(getOrderBy(asc, issue, orderBy))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val totalSize = query.size


        return PageImpl(query, pageable, totalSize.toLong())
    }

    override fun save(issue: Issue): Issue {
        return issueJpaRepository.save(issue)
    }

    override fun findByIdOrNull(id: Long): Issue? {
        return issueJpaRepository.findByIdAndDeletedAtIsNull(id)
    }

    override fun delete(issue: Issue) {
        issueJpaRepository.delete(issue)
    }

    override fun deletedIssue() {
        issueJpaRepository.deletedIssue()
    }

    override fun findAllDeleted(pageable: Pageable): Page<Issue> {
        val query = queryFactory
            .selectFrom(issue)
            .where(issue.deletedAt.isNotNull)
            .fetch()

        val totalSize = query.size.toLong()

        return PageImpl(query, pageable, totalSize)
    }

    private fun topicToContent(topic: String, content: String): BooleanExpression {

        return when(topic){
            "title" -> issue.title.like("%$content%")
            "description" -> issue.description.like("%$content%")
            "nickname" -> issue.member.nickname.like("%$content%")
            "priority" -> issue.priority.eq(Priority.valueOf(content))
            "workingStatus" -> issue.workingStatus.eq(WorkingStatus.valueOf(content))
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