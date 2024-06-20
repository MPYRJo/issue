package com.yoong.myissue.domain.member.entity

import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.team.entity.Team
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType


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
    @JdbcType(PostgreSQLEnumJdbcType::class)
    val role : Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    val team : Team
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null


}