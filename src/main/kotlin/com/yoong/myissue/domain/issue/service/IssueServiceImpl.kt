package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.common.annotationGather.CheckAuthentication
import com.yoong.myissue.common.annotationGather.CheckMyTeam
import com.yoong.myissue.common.enumGather.AuthenticationType
import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.repository.IssueRepository
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.service.DUMMY_TEAM
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.exception.clazz.DummyTeamException
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class IssueServiceImpl(
    private val issueRepository: IssueRepository,
    private val memberService: ExternalMemberService,
    private val teamService: ExternalTeamService
): IssueService{

    @CheckAuthentication(AuthenticationType.ALL)
    override fun createIssue(issueCreateRequest: IssueCreateRequest, email: String): String {

        val member = memberService.searchEmail(email)

        if(member.getTeam() == teamService.getDummyTeam()) throw DummyTeamException("현재는 팀 소속이 없어서 이슈 작성이 불가능 합니다")


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
    @CheckMyTeam
    override fun getIssue(email: String, issueId: Long): IssueResponse {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        return issue.toIssueResponse(true)
    }

    @CheckAuthentication(AuthenticationType.ALL)
    @Transactional(readOnly = true)
    override fun getIssueList(
        email: String,
        searchIssueListRequest: SearchIssueListRequest,
        pageable: Pageable
    ): Page<IssueResponse> {

        val teamId = memberService.searchEmail(email).getTeam().getId()!!

        if(teamId == DUMMY_TEAM) throw DummyTeamException("임시 팀 맴버는 이슈를 볼 수 없습니다")

        val team = teamService.getTeamById(teamId)

        return issueRepository.findAllIssueList(
            searchIssueListRequest.topic,
            searchIssueListRequest.content,
            searchIssueListRequest.asc,
            searchIssueListRequest.orderBy,
            team,
            pageable
        ).map { it.toIssueResponse(false) }

    }

    @CheckAuthentication(AuthenticationType.ALL)
    @CheckMyTeam
    override fun updateIssue(email: String, issueId: Long, issueUpdateRequest: IssueUpdateRequest): String {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        issue.update(issueUpdateRequest)

        issueRepository.save(issue)

        return "업데이트 가 완료 되었습니다"
    }

    @CheckAuthentication(AuthenticationType.ALL)
    @CheckMyTeam
    override fun deleteIssue(email: String, issueId: Long): String {

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        issueRepository.delete(issue)
        //TODO("가져온 이슈를 SpringScheduler 에 의해서 3시간(90일) 이후에 자동 삭제")
        return "삭제가 완료 되었습니다, 이슈 번호 $issueId"
    }


}

