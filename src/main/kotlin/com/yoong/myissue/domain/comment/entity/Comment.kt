package com.yoong.myissue.domain.comment.entity

import com.yoong.myissue.domain.comment.dto.CommentResponse
import com.yoong.myissue.domain.comment.dto.UpdateCommentRequest
import com.yoong.myissue.domain.comment.enumGather.CheckingStatus
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.member.entity.Member
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import java.time.LocalDateTime

@SQLDelete(sql = "UPDATE comment SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is null")
@Entity
@Table(name = "comment")
class Comment(

    @Column(unique = false, nullable = false)
    private var content: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    private var checkingStatus: CheckingStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private val issue: Issue,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id : Long? = null

    @Column(name="created_at", nullable = false, unique = false)
    @CreationTimestamp
    private val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name="deleted_at", nullable = false, unique = false)
    private var deletedAt: LocalDateTime? = null


    fun update(updateCommentRequest: UpdateCommentRequest) {
        this.content = updateCommentRequest.content
        this.checkingStatus = updateCommentRequest.checkingStatus
    }

    fun toCommentResponse(): CommentResponse {
        return CommentResponse(
            id = this.id!!,
            content = this.content,
            checkingStatus = this.checkingStatus,
            createdAt = this.createdAt,
            nickname = member.getNickname(),
        )
    }

    fun getMember() = this.member.getId()
}