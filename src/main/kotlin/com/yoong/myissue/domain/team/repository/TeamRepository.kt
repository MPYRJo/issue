package com.yoong.myissue.domain.team.repository

import com.yoong.myissue.domain.team.entity.Team

interface TeamRepository {

    fun findByIdOrNull(id: Long): Team?

    fun save(team: Team): Team

    fun findAll(): List<Team>

    fun delete(team: Team)

    fun existsByName(name: String): Boolean
}