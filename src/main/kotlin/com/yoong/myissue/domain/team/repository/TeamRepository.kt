package com.yoong.myissue.domain.team.repository

import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository: JpaRepository<Team, Long> {
}