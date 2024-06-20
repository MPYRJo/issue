package com.yoong.myissue.domain.issue.entity

import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.enum.Priority
import com.yoong.myissue.domain.issue.enum.WorkingStatus
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.team.entity.Team
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime


@SQLDelete(sql = "UPDATE issue c SET c.deleted_at = current_timestamp WHERE c.id = ?")
@SQLRestriction("deleted_at = null")
@Entity
@Table(name = "issue")
class Issue(

    @Column(nullable = false, unique = false)
    var title: String,

    @Column(nullable = true, unique = false)
    var description : String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    var priority : Priority,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    var workingStatus : WorkingStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    val team: Team,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name="created_at", nullable = false, unique = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name="updated_at", nullable = false, unique = false)
    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name="deleted_at", nullable = false, unique = false)
    var deletedAt: LocalDateTime? = null

    fun update(issueUpdateRequest: IssueUpdateRequest){
        this.title = issueUpdateRequest.title ?: this.title
        this.description = issueUpdateRequest.description ?: this.description
        this.priority = issueUpdateRequest.priority ?: this.priority
        this.workingStatus = issueUpdateRequest.workingStatus ?: this.workingStatus
    }
}