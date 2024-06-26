package com.yoong.myissue.domain.team.repository

import com.yoong.myissue.domain.team.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamJpaRepository: JpaRepository<Team, Long> {

    fun existsByName(name: String): Boolean

}