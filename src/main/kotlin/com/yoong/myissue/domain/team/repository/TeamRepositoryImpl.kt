package com.yoong.myissue.domain.team.repository

import com.yoong.myissue.domain.team.entity.Team
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class TeamRepositoryImpl(
    private val teamJpaRepository: TeamJpaRepository
): TeamRepository {

    override fun findByIdOrNull(id: Long): Team? {
        return teamJpaRepository.findByIdOrNull(id)
    }

    override fun save(team: Team): Team {
        return teamJpaRepository.save(team)
    }

    override fun findAll(): List<Team> {
        return teamJpaRepository.findAll()
    }

    override fun delete(team: Team) {
        return teamJpaRepository.delete(team)
    }

    override fun existsByName(name: String): Boolean {
        return teamJpaRepository.existsByName(name)
    }
}