package com.yoong.myissue.domain.team.controller

import com.yoong.myissue.common.annotationGather.FailedLogin
import com.yoong.myissue.domain.team.dto.TeamAdminInviteRequest
import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.service.TeamServiceImpl
import com.yoong.myissue.infra.security.config.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/team")
class TeamController(
    private val teamService: TeamServiceImpl
) {

    @PostMapping
    @FailedLogin
    fun createTeam(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody createTeamRequest: TeamRequest
    ): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(userPrincipal!!.email, createTeamRequest))
    }

    @GetMapping("/{teamId}")
    @FailedLogin
    fun getTeamById(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable teamId: Long,
    ): ResponseEntity<TeamResponse> {

        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamById(userPrincipal!!.email, teamId))
    }

    @GetMapping
    @FailedLogin
    fun getTeamList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        ): ResponseEntity<List<TeamResponse>>{

        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamList(userPrincipal!!.email))
    }

    @PutMapping("/{teamId}")
    @FailedLogin
    fun updateTeam(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable teamId: Long,
        @RequestBody updateTeamRequest: TeamRequest
    ):ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(teamService.updateTeam(userPrincipal!!.email, teamId, updateTeamRequest))
    }


    @DeleteMapping("/{teamId}")
    @FailedLogin
    fun deleteTeam(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable teamId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(teamService.deleteTeam(userPrincipal!!.email, teamId ))
    }

    @PatchMapping("/invite/{memberId}")
    @FailedLogin
    fun inviteMember(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable memberId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(teamService.inviteTeam(userPrincipal!!.email, memberId ))
    }

    @PatchMapping("/invite/admin")
    @FailedLogin
    fun inviteMemberByAdmin(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody teamAdminInviteRequest: TeamAdminInviteRequest
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                teamService.inviteMemberByAdmin(userPrincipal!!.email, teamAdminInviteRequest.memberId, teamAdminInviteRequest.teamId
                ))
    }

    @DeleteMapping("/fired/{memberId}")
    @FailedLogin
    fun firedMember(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable memberId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(teamService.firedMember(userPrincipal!!.email, memberId))
    }
}