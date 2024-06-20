package com.yoong.myissue.domain.comment.entity

import com.yoong.myissue.domain.comment.enum.CheckingStatus
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.member.entity.Member
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(

    @Column(unique = false, nullable = false)
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    val checkingStatus: CheckingStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    val issue: Issue,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name="created_at", nullable = false, unique = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name="deleted_at", nullable = false, unique = false)
    var deletedAt: LocalDateTime? = null
}