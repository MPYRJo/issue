package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.repository.IssueRepository
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.exception.`class`.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val memberService: ExternalMemberService,
){


    fun createIssue(issueCreateRequest: IssueCreateRequest, email: String): String {

        val member = memberService.searchEmail(email)

        val savedIssue = issueRepository.save(
            Issue(
                title = issueCreateRequest.title,
                description = issueCreateRequest.description,
                priority = issueCreateRequest.priority,
                workingStatus = issueCreateRequest.workingStatus,
                member = member,
                team = member.team,
            )
        )

        return "이슈가 등록 되었습니다 이슈 번호 : ${savedIssue.getId()}"
    }

    @Transactional(readOnly = true)
    fun getIssue(issueId: Long): IssueResponse {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        return issue.toIssueResponse()
    }

    fun getIssueList(): List<IssueResponse> {

        val issueList = issueRepository.findAll()

        return issueList.map { it.toIssueResponse() }
    }

    fun updateIssue(issueId: Long, issueUpdateRequest: IssueUpdateRequest): String {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        issue.update(issueUpdateRequest)

        val savedData = issueRepository.save(issue)

        return "업데이트 가 완료 되었습니다, 이슈 번호 ${savedData.getId()}"
    }

    fun deleteIssue(issueId: Long): String {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        issueRepository.delete(issue)
        //TODO("가져온 이슈를 SpringScheduler 에 의해서 3시간(90일) 이후에 자동 삭제")
        return "삭제가 완료 되었습니다, 이슈 번호 ${issue.getId()}"
    }


}

