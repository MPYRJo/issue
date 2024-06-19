package com.yoong.myissue.domain.member.entity

import com.yoong.myissue.domain.issue.enum.Role
import jakarta.persistence.*


@Entity
@Table(name = "member")
class Member (

    @Column(name = "nickname", nullable = false, unique = true)
    val nickname: String,

    @Column(name = "email", nullable = false, unique = true)
    val email : String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role : Role,

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "member")
    val team : Team

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
}