package com.yoong.myissue.domain.team.service

import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

const val DUMMY_TEAM: Long = 1L

@Service
class ExternalTeamService(
    private val teamRepository: TeamRepository
) {

    fun getDummyTeam(): Team {
        return teamRepository.findByIdOrNull(DUMMY_TEAM)!!
    }

    fun validDummyTeam(team: Team):Boolean {
       return team != teamRepository.findByIdOrNull(DUMMY_TEAM)!!
    }

    fun getTeamById(teamId: Long): Team {
        return teamRepository.findByIdOrNull(teamId) ?: throw ModelNotFoundException("팀", teamId.toString())
    }
}