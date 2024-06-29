package com.yoong.myissue.domain.issue.controller

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import com.yoong.myissue.domain.issue.service.IssueService
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import com.yoong.myissue.exception.clazz.InvalidCredentialException
import com.yoong.myissue.infra.s3.S3Manager
import com.yoong.myissue.infra.security.config.UserPrincipal
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService,
    private val s3Manager: S3Manager,
) {


    @PostMapping
    fun createIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @Valid @RequestPart issueCreateRequest : IssueCreateRequest,
        @RequestPart image: MultipartFile,
        bindingResult: BindingResult
    ): ResponseEntity<String> {

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")


        if(bindingResult.hasErrors()) throw IllegalArgumentException(bindingResult.fieldError!!.defaultMessage.toString())

        val uploadImage = s3Manager.uploadImage(image)
        val imageUrl = s3Manager.getFile(uploadImage)
        println()

        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(userPrincipal.email, issueCreateRequest, imageUrl))
    }

    @GetMapping("/{issueId}")
    fun getIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<IssueResponse>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssue(userPrincipal.email,issueId))
    }

    @GetMapping()
    fun getIssueList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @ModelAttribute searchIssueListRequest: SearchIssueListRequest,
    ): ResponseEntity<Page<IssueResponse>>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        val pageable = PageRequest.of(searchIssueListRequest.page, searchIssueListRequest.pageSize)

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueList(userPrincipal.email, searchIssueListRequest, pageable))
    }


    @GetMapping("/deleted")
    fun getDeletedIssueList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestParam(value = "page_number", defaultValue = "0") page: Int,
        @RequestParam(value = "page_size", defaultValue = "10") pageSize: Int,
        @RequestParam(value = "sort_by", defaultValue = "id") sortBy: String,
        @RequestParam(value = "ascend", defaultValue = "true") isAsc: Boolean,
        ): ResponseEntity<Page<IssueResponse>>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")
        
        val orderBy = if(isAsc) Sort.by(sortBy).ascending() else Sort.by(sortBy).descending()
        val pageable = PageRequest.of(page, pageSize, orderBy)

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getDeletedIssue(userPrincipal.email, pageable))
    }

    @PutMapping("/{issueId}")
    fun updateIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
        @Valid @RequestBody issueUpdateRequest : IssueUpdateRequest,
        @RequestPart image: MultipartFile,
        bindingResult: BindingResult
    ): ResponseEntity<String>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")

        if(bindingResult.hasErrors()) throw IllegalArgumentException(bindingResult.fieldError!!.defaultMessage.toString())

        val uploadImage = s3Manager.uploadImage(image)
        val imageUrl = s3Manager.getFile(uploadImage)


        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(userPrincipal.email, issueId, issueUpdateRequest, imageUrl))
    }

    @DeleteMapping("/{issueId}")
    fun deleteIssue(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<String>{

        if(userPrincipal == null) throw InvalidCredentialException("로그인을 해 주세요")
        
        return ResponseEntity.status(HttpStatus.OK).body(issueService.deleteIssue(userPrincipal.email, issueId))
    }
}