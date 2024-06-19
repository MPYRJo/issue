package com.yoong.myissue.domain.team.repository

import com.yoong.myissue.domain.team.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository: JpaRepository<Team, Long> {
}