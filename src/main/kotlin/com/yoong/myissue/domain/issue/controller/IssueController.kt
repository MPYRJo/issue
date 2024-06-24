package com.yoong.myissue.domain.issue.controller

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.service.IssueService
import com.yoong.myissue.exception.clazz.NoAuthenticationException
import com.yoong.myissue.infra.security.config.UserPrincipal
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService
) {

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_LEADER')")
    @PostMapping
    fun createIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @Valid @RequestBody issueCreateRequest : IssueCreateRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String> {

        if(userPrincipal == null) throw NoAuthenticationException()

        if(bindingResult.hasErrors()) throw IllegalArgumentException(bindingResult.fieldError!!.defaultMessage.toString())

        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueCreateRequest, userPrincipal.email))
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_LEADER')")
    @GetMapping("/{issueId}")
    fun getIssue(
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<IssueResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssue(issueId))
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_LEADER')")
    @GetMapping()
    fun getIssueList(
        @RequestParam(required = false,) title: String?,
        @RequestParam(required = false,) description: String?,
        @RequestParam(required = false,) nickName: String?,
        @RequestParam(required = false,) startDate: LocalDateTime?,
    ): ResponseEntity<List<IssueResponse>>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueList())
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_LEADER')")
    @PutMapping("/{issueId}")
    fun updateIssue(
        @PathVariable("issueId") issueId: Long,
        @RequestBody issueUpdateRequest : IssueUpdateRequest
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(issueId, issueUpdateRequest))
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_LEADER')")
    @DeleteMapping("/{issueId}")
    fun deleteIssue(
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.deleteIssue(issueId))
    }
}