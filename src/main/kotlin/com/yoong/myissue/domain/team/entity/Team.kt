package com.yoong.myissue.domain.team.entity

import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.member.entity.Member
import jakarta.persistence.*


@Entity
@Table(name = "team")
class Team(

    @Column(nullable = false, unique = true)
    val name: String,

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    val members: List<Member>,

    @OneToMany(mappedBy = "issue", orphanRemoval = true, fetch = FetchType.LAZY)
    val issues: List<Issue>
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
}