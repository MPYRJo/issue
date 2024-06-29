package com.yoong.myissue.domain.issue.entity

import com.yoong.myissue.domain.comment.entity.Comment
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.enumGather.Priority
import com.yoong.myissue.domain.issue.enumGather.WorkingStatus
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.team.entity.Team
import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.Table
import org.hibernate.annotations.*
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import java.time.LocalDateTime


@SQLDelete(sql = "UPDATE issue SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Entity
@Table(name = "issue")
class Issue(

    @Column(nullable = false, unique = false)
    private var title: String,

    @Column(nullable = true, unique = false)
    private var description : String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    private var priority : Priority,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    private var workingStatus : WorkingStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private val team: Team,

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "issue_id")
    private val comment: List<Comment>,

    @Column(name="image_url", nullable = true, columnDefinition = "TEXT")
    private var imageUrl: String? = null

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id : Long? = null

    @Column(name="created_at", nullable = false, unique = false)
    @CreationTimestamp
    private val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name="updated_at", nullable = false, unique = false)
    @UpdateTimestamp
    private var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name="deleted_at", nullable = true, unique = false)
    private var deletedAt: LocalDateTime? = null



    fun update(issueUpdateRequest: IssueUpdateRequest){
        this.title = if(issueUpdateRequest.title == "") this.title else issueUpdateRequest.title?: this.title
        this.description = if(issueUpdateRequest.description == "") this.title else issueUpdateRequest.description ?: this.description
        this.priority = issueUpdateRequest.priority
        this.workingStatus = issueUpdateRequest.workingStatus
        this.imageUrl = issueUpdateRequest.imageUrl
    }

    fun toIssueResponse(isDetailContent: Boolean): IssueResponse {
        return IssueResponse(
            id = id!!,
            title = title,
            description = description,
            priority = priority,
            workingStatus = workingStatus,
            nickname = member.getNickname(),
            teamName = team.getTeamName(),
            createdAt = createdAt,
            comment = if(isDetailContent) comment.map { it.toCommentResponse() } else emptyList(),
            imageUrl = if(isDetailContent) imageUrl?:"" else ""
        )
    }

    fun getCreatedAt() = this.createdAt
    fun getMember() = this.member.getId()
    fun getTeam() = this.team

}