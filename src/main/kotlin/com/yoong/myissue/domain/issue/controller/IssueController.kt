package com.yoong.myissue.domain.issue.controller

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import com.yoong.myissue.domain.issue.service.IssueService
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.exception.clazz.NoAuthenticationException
import com.yoong.myissue.infra.security.config.UserPrincipal
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


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
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<IssueResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssue(issueId))
    }

    @GetMapping()
    fun getIssueList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @ModelAttribute searchIssueListRequest: SearchIssueListRequest,
    ): ResponseEntity<List<IssueResponse>>{
        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueList(searchIssueListRequest, userPrincipal.email))
    }

    @PutMapping("/{issueId}")
    fun updateIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
        @RequestBody issueUpdateRequest : IssueUpdateRequest
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(issueId, issueUpdateRequest))
    }

    @DeleteMapping("/{issueId}")
    fun deleteIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.deleteIssue(issueId))
    }
}