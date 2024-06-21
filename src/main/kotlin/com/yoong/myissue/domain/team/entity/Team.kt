package com.yoong.myissue.domain.team.entity

import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.member.dto.MemberResponse
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.team.dto.TeamResponse
import jakarta.persistence.*


@Entity
@Table(name = "team")
class Team(

    @Column(nullable = false, unique = true)
    private val name: String,

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private val members: List<Member>,

    @OneToMany(mappedBy = "team", orphanRemoval = true, fetch = FetchType.LAZY)
    private val issues: List<Issue>
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id : Long? = null

    fun toTeamResponse(): TeamResponse {
        return TeamResponse(
            id = id!!,
            name = name,
            issue = issues.map { it.toIssueResponse() },
            member = members.map { it.toMemberResponse() }
        )
    }

    fun getTeamName() = this.name
}
