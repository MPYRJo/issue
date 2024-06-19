package com.yoong.myissue.domain.issue.controller

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.infra.dto.UpdateResponse
import com.yoong.myissue.domain.issue.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService
) {

    @PostMapping
    fun createIssue(
        @RequestBody issueCreateRequest : IssueCreateRequest
    ): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueCreateRequest))
    }

    @GetMapping("/{issueId}")
    fun getIssue(
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<IssueResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssue(issueId))
    }

    @GetMapping()
    fun getIssueList(
        @RequestParam(required = false,) title: String?,
        @RequestParam(required = false,) description: String?,
        @RequestParam(required = false,) nickName: String?,
        @RequestParam(required = false,) startDate: LocalDateTime?,
    ): ResponseEntity<List<IssueResponse>>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueList())
    }

    @PutMapping("/{issueId}")
    fun updateIssue(
        @PathVariable("issueId") issueId: Long,
        @RequestBody issueUpdateRequest : IssueUpdateRequest
    ): ResponseEntity<UpdateResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(issueId, issueUpdateRequest))
    }

    @DeleteMapping("/{issueId}")
    fun deleteIssue(
        @PathVariable("issueId") issueId: Long,
    ): ResponseEntity<String>{

        return ResponseEntity.status(HttpStatus.OK).body(issueService.deleteIssue(issueId))
    }
}