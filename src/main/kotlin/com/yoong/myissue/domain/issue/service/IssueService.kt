package com.yoong.myissue.domain.issue.service


import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.common.enumGather.AuthenticationType
import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.issue.repository.IssueRepository
import com.yoong.myissue.domain.issue.repository.QueryDslIssueRepository
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.service.DUMMY_TEAM
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.exception.clazz.DummyTeamException
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val memberService: ExternalMemberService,
    private val teamService: ExternalTeamService,
    private val validAuthentication: ValidAuthentication,
    private val queryDslIssueRepository : QueryDslIssueRepository
){

    fun createIssue(email: String, issueCreateRequest: IssueCreateRequest, imageUrl: String?): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ALL)

        if(member.getTeam() == teamService.getDummyTeam()) throw DummyTeamException("현재는 팀 소속이 없어서 이슈 작성이 불가능 합니다")


        issueRepository.save(
            Issue(
                title = issueCreateRequest.title,
                description = issueCreateRequest.description,
                priority = issueCreateRequest.priority,
                workingStatus = issueCreateRequest.workingStatus,
                member = member,
                team = member.getTeam(),
                comment = listOf(),
                imageUrl = imageUrl,
            )
        )


        return "이슈가 등록 되었습니다 이슈 이름 : ${issueCreateRequest.title}"
    }

    @Transactional(readOnly = true)
    fun getIssue(email: String, issueId: Long): IssueResponse {

        val member = memberService.searchEmail(email)

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        if(member.getRole() != Role.ADMIN && member.getTeam() != issue.getTeam()) throw IllegalArgumentException("다른 팀 정보는 선택 할 수 없습니다")

        return issue.toIssueResponse(true)
    }

    @Transactional(readOnly = true)
    fun getIssueList(
        email: String,
        searchIssueListRequest: SearchIssueListRequest,
        pageable: Pageable
    ): Page<IssueResponse> {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ALL)

        val teamId = memberService.searchEmail(email).getTeam().getId()!!

        if(teamId == DUMMY_TEAM) throw DummyTeamException("임시 팀 맴버는 이슈를 볼 수 없습니다")

        val team = teamService.getTeamById(teamId)

        return queryDslIssueRepository.findAllIssueList(
            searchIssueListRequest.topic,
            searchIssueListRequest.content,
            searchIssueListRequest.asc,
            searchIssueListRequest.orderBy,
            team,
            pageable
        ).map { it.toIssueResponse(false) }

    }

    fun getDeletedIssue(email: String, pageable: Pageable): Page<IssueResponse> {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ADMIN)

        return issueRepository.findAllDeleted(pageable).map { it.toIssueResponse(false) }
    }

    fun updateIssue(email: String, issueId: Long, issueUpdateRequest: IssueUpdateRequest, imageUrl: String?): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ALL)

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        if(member.getRole() != Role.ADMIN && member.getTeam() != issue.getTeam()) throw IllegalArgumentException("다른 팀 정보는 선택 할 수 없습니다")

        issue.update(issueUpdateRequest)

        issueRepository.save(issue)

        return "업데이트 가 완료 되었습니다"
    }

    fun deleteIssue(email: String, issueId: Long): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ALL)

        val issue = issueRepository.findByIdOrNull(issueId) ?: throw ModelNotFoundException("이슈", issueId.toString())

        if(member.getRole() != Role.ADMIN && member.getTeam() != issue.getTeam()) throw IllegalArgumentException("다른 팀 정보는 선택 할 수 없습니다")

        issueRepository.delete(issue)
        //TODO("가져온 이슈를 SpringScheduler 에 의해서 3시간(90일) 이후에 자동 삭제")
        return "삭제가 완료 되었습니다, 이슈 번호 $issueId"
    }


}

