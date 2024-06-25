package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.repository.IssueRepository
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ModelAttribute
import java.time.LocalDateTime

@Transactional
@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val memberService: ExternalMemberService,
){


    private val log = LoggerFactory.getLogger("테스트 입니다")
    fun createIssue(issueCreateRequest: IssueCreateRequest, email: String): String {

        val member = memberService.searchEmail(email)


        issueRepository.save(
            Issue(
                title = issueCreateRequest.title,
                description = issueCreateRequest.description,
                priority = issueCreateRequest.priority,
                workingStatus = issueCreateRequest.workingStatus,
                member = member,
                team = member.getTeam(),
                comment = listOf()
            )
        )

        return "이슈가 등록 되었습니다 이슈 이름 : ${issueCreateRequest.title}"
    }

    @Transactional(readOnly = true)
    fun getIssue(issueId: Long): IssueResponse {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        log.info(issue.toString())
        return issue.toIssueResponse(true)
    }

    @Transactional(readOnly = true)
    fun getIssueList(
        searchIssueListRequest: SearchIssueListRequest,
        email: String,
        pageable: Pageable
    ): Page<IssueResponse> {

        return issueRepository.findAll(
            searchIssueListRequest.topic,
            searchIssueListRequest.content,
            searchIssueListRequest.asc,
            searchIssueListRequest.orderBy,
            pageable
        ).map { it.toIssueResponse(false) }

    }

    fun updateIssue(issueId: Long, issueUpdateRequest: IssueUpdateRequest): String {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        issue.update(issueUpdateRequest)

        issueRepository.save(issue)

        return "업데이트 가 완료 되었습니다"
    }

    fun deleteIssue(issueId: Long): String {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        issueRepository.delete(issue)
        //TODO("가져온 이슈를 SpringScheduler 에 의해서 3시간(90일) 이후에 자동 삭제")
        return "삭제가 완료 되었습니다, 이슈 번호 $issueId"
    }


}

