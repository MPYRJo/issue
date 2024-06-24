package com.yoong.myissue.domain.team.controller

import com.yoong.myissue.domain.team.dto.TeamAdminInviteRequest
import com.yoong.myissue.domain.team.dto.TeamRequest
import com.yoong.myissue.domain.team.dto.TeamResponse
import com.yoong.myissue.domain.team.service.TeamService
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.infra.security.config.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable teamId: Long,
    ): ResponseEntity<TeamResponse> {

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamById(teamId, userPrincipal.email))
    }

    @GetMapping
    fun getTeamList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        ): ResponseEntity<List<TeamResponse>>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamList(userPrincipal.email))
    }

    @PutMapping("/{teamId}")
    fun updateTeam(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable teamId: Long,
        @RequestBody updateTeamRequest: TeamRequest
    ):ResponseEntity<String>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(teamService.updateTeam(teamId, userPrincipal.email, updateTeamRequest))
    }


    @DeleteMapping("/{teamId}")
    fun deleteTeam(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable teamId: Long,
    ): ResponseEntity<String>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(teamService.deleteTeam(teamId, userPrincipal.email))
    }

    @PatchMapping("/invite/{memberId}")
    fun inviteMember(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable memberId: Long,
    ): ResponseEntity<String>{
        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(teamService.inviteTeam(memberId, userPrincipal.email))
    }

    @PatchMapping("/invite/admin")
    fun inviteMemberByAdmin(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody teamAdminInviteRequest: TeamAdminInviteRequest
    ): ResponseEntity<String>{
        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                teamService.inviteMemberByAdmin(teamAdminInviteRequest.memberId, userPrincipal.email,teamAdminInviteRequest.teamId
                ))
    }

    @DeleteMapping("/fired/{memberId}")
    fun firedMember(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable memberId: Long,
    ): ResponseEntity<String>{
        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(teamService.firedMember(memberId, userPrincipal.email))
    }
}