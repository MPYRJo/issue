package com.yoong.myissue.domain.issue.controller

import com.yoong.myissue.common.annotationGather.FailedLogin
import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import com.yoong.myissue.domain.issue.service.IssueServiceImpl
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.exception.clazz.NoAuthenticationException
import com.yoong.myissue.infra.security.config.UserPrincipal
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueServiceImpl
) {

    @PostMapping
    @FailedLogin
    fun createIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @Valid @RequestBody issueCreateRequest : IssueCreateRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String> {

        if(bindingResult.hasErrors()) throw IllegalArgumentException(bindingResult.fieldError!!.defaultMessage.toString())

        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(userPrincipal!!.email, issueCreateRequest))
    }

    @GetMapping("/{issueId}")
    @FailedLogin
    fun getIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<IssueResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssue(userPrincipal!!.email,issueId))
    }

    @GetMapping()
    @FailedLogin
    fun getIssueList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @ModelAttribute searchIssueListRequest: SearchIssueListRequest,
    ): ResponseEntity<Page<IssueResponse>>{

        val pageable = PageRequest.of(searchIssueListRequest.page, searchIssueListRequest.pageSize)

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueList(userPrincipal!!.email, searchIssueListRequest, pageable))
    }

    @FailedLogin
    @GetMapping("/deleted")
    fun getDeletedIssueList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestParam(value = "page_number", defaultValue = "0") page: Int,
        @RequestParam(value = "page_size", defaultValue = "10") pageSize: Int,
        @RequestParam(value = "sort_by", defaultValue = "id") sortBy: String,
        @RequestParam(value = "ascend", defaultValue = "true") isAsc: Boolean,
        ): ResponseEntity<Page<IssueResponse>>{
        val orderBy = if(isAsc) Sort.by(sortBy).ascending() else Sort.by(sortBy).descending()
        val pageable = PageRequest.of(page, pageSize, orderBy)

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getDeletedIssue(userPrincipal!!.email, pageable))
    }

    @PutMapping("/{issueId}")
    @FailedLogin
    fun updateIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
        @Valid @RequestBody issueUpdateRequest : IssueUpdateRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String>{
        if(bindingResult.hasErrors()) throw IllegalArgumentException(bindingResult.fieldError!!.defaultMessage.toString())


        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(userPrincipal!!.email, issueId, issueUpdateRequest))
    }

    @DeleteMapping("/{issueId}")
    @FailedLogin
    fun deleteIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.deleteIssue(userPrincipal!!.email, issueId))
    }
}