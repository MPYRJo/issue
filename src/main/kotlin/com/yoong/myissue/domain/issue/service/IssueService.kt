package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.repository.IssueRepository
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.exception.`class`.ModelNotFoundException
import com.yoong.myissue.infra.dto.UpdateResponse
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

        return "이슈가 등록 되었습니다 이슈 번호 : ${savedIssue.id}"
    }

    @Transactional(readOnly = true)
    fun getIssue(issueId: Long): IssueResponse {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        return IssueResponse.from(issue)
    }

    fun getIssueList(): List<IssueResponse> {

        val issueList = issueRepository.findAll()

        return issueList.map { IssueResponse.from(it) }
    }

    fun updateIssue(issueId: Long, issueUpdateRequest: IssueUpdateRequest): UpdateResponse {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        issue.update(issueUpdateRequest)
        TODO("이슈 변경 사항을 반환")
    }

    fun deleteIssue(issueId: Long): String {
        //TODO("이슈 아이디 를 받이서 아이디 가 없는 경우 ModelNotFoundException")
        //TODO("가져온 이슈를 SoftDelete")
        //TODO("가져온 이슈를 SpringScheduler 에 의해서 3시간(90일) 이후에 자동 삭제")
        TODO("삭제 완료 내용을 반환")
    }


}

