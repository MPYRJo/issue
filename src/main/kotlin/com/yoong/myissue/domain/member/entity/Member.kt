package com.yoong.myissue.domain.member.entity

import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.common.clazz.PasswordManagement
import com.yoong.myissue.domain.member.dto.MemberResponse
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.infra.security.jwt.JwtPlugin
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType


@Entity
@Table(name = "member")
class Member (

    @Column(name = "nickname", nullable = false, unique = true)
    private val nickname: String,

    @Column(name = "email", nullable = false, unique = true)
    private val email : String,

    @Column(name = "password", nullable = false)
    private val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    private var role : Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private var team : Team
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id : Long? = null

    fun validPassword(passwordManagement: PasswordManagement, password: String):Boolean{
        return passwordManagement.valid(this.password, password)
    }

    fun toMemberResponse(): MemberResponse {
        return MemberResponse(
            id = id!!,
            email = email,
            nickname = nickname,
            role = role
        )
    }

    fun getAccessToken(jwtPlugin: JwtPlugin): String {
       return jwtPlugin.generateAccessToken(this.id.toString(), this.email, this.role.name)
    }

    fun getId() = id
    fun getTeam() = team
    fun getNickname() = nickname
    fun getRole() = role

    fun promotionLeader(team: Team) {
        this.team = team
        role = Role.LEADER
    }

    fun updateTeam(team: Team) {
        this.team = team
    }
}