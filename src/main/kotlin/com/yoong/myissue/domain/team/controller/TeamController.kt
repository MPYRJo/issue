package com.yoong.myissue.domain.team.controller

import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.service.TeamService
import com.yoong.myissue.exception.`class`.InvalidCredentialException
import com.yoong.myissue.infra.dto.UpdateResponse
import com.yoong.myissue.infra.security.config.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/team")
class TeamController(
    private val teamService: TeamService
) {

    @PostMapping
    fun createTeam(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody createTeamRequest: TeamRequest
    ): ResponseEntity<String> {
        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(createTeamRequest, userPrincipal.email))
    }

    @GetMapping("/{teamId}")
    fun getTeamById(
        @PathVariable teamId: Long,
    ): ResponseEntity<TeamResponse> {

        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamById(teamId))
    }

    @GetMapping
    fun getTeamList(): ResponseEntity<List<TeamResponse>>{

        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamList())
    }

    @PutMapping("/{teamId}")
    fun updateTeam(
        @PathVariable teamId: Long,
        @RequestBody updateTeamRequest: TeamRequest
    ):ResponseEntity<UpdateResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(teamService.updateTeam(teamId, updateTeamRequest))
    }


    @DeleteMapping("/{teamId}")
    fun deleteTeam(
        @PathVariable teamId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(teamService.deleteTeam(teamId))
    }
}