package com.yoong.myissue.domain.issue.controller

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.service.IssueService
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.exception.clazz.NoAuthenticationException
import com.yoong.myissue.infra.security.config.UserPrincipal
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService
) {

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

    @GetMapping("/{issueId}")
    fun getIssue(
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<IssueResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssue(issueId))
    }

    @GetMapping()
    fun getIssueList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestParam(required = false,) title: String?,
        @RequestParam(required = false,) description: String?,
        @RequestParam(required = false,) nickName: String?,
        @RequestParam(required = false,) startDate: LocalDateTime?,
    ): ResponseEntity<List<IssueResponse>>{
        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueList(title, description, nickName, startDate, userPrincipal.email))
    }

    @PutMapping("/{issueId}")
    fun updateIssue(
        @PathVariable("issueId") issueId: Long,
        @RequestBody issueUpdateRequest : IssueUpdateRequest
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(issueId, issueUpdateRequest))
    }

    @DeleteMapping("/{issueId}")
    fun deleteIssue(
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.deleteIssue(issueId))
    }
}