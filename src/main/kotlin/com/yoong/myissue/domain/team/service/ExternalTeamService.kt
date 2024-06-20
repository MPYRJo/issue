package com.yoong.myissue.domain.team.service

import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.repository.TeamRepository
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
}