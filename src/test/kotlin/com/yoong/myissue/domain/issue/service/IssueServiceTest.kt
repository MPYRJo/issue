package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.SearchIssueListRequest
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.enumGather.Priority
import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.issue.enumGather.WorkingStatus
import com.yoong.myissue.domain.issue.repository.IssueRepository
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.entity.Team
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.exception.clazz.DummyTeamException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class IssueServiceTest {

    private val issueRepository = mockk<IssueRepository>()
    private val teamService = mockk<ExternalTeamService>()
    private val memberService = mockk<ExternalMemberService>()

    private val issueService = IssueServiceImpl(
        issueRepository, memberService, teamService
    )

    @Test
    fun `이슈 등록이 정상적으로 수행될 경우 이슈가 등록 되었습니다 이슈 이름 제목 이 나오는지 확인`() {


        val issueCreateRequest = IssueCreateRequest(
            "title",
            "description",
            Priority.DANGER,
            WorkingStatus.DONE
        )

        every { memberService.searchEmail(any()) } returns MEMBER

        every { teamService.getDummyTeam() } returns DUMMY

        every { issueRepository.save(any()) } returns ISSUE

        val result = issueService.createIssue("test@test.com", issueCreateRequest, null)

        result shouldBe "이슈가 등록 되었습니다 이슈 이름 : title"
    }

    @Test
    fun `더미팀일 경우 현재는 팀 소속이 없어서 이슈 작성이 불가능 합니다`() {


        val issueCreateRequest = IssueCreateRequest(
            "title",
            "description",
            Priority.DANGER,
            WorkingStatus.DONE
        )

        val dummyMember = Member(
            "test",
            "test@test.com",
            "password",
            Role.USER,
            DUMMY
        )

        every { memberService.searchEmail(any()) } returns dummyMember

        every { teamService.getDummyTeam() } returns DUMMY

        every { issueRepository.save(any()) } returns ISSUE

        shouldThrow<DummyTeamException> { issueService.createIssue("test@test.com", issueCreateRequest, null) }
            .let {
                it.message shouldBe "현재는 팀 소속이 없어서 이슈 작성이 불가능 합니다"
            }

    }

    @Test
    fun `이슈를 단건으로 가져올 경우 제대로 가져오는 지 확인`(){

        every { issueRepository.findByIdOrNull(any()) } returns ISSUE

        val result = issueService.getIssue("test@test.com", 1L)


        result.priority shouldBe Priority.DANGER
        result.comment shouldBe listOf()
        result.workingStatus shouldBe WorkingStatus.DONE
        result.title shouldBe "title"
        result.description shouldBe "description"
    }


    @Test
    fun `이슈를 여러건으로 가져올 때 제목을 title 로 검색 할 경우 title 제목만 가져오는지 확인`(){

        every { issueRepository.findByIdOrNull(any()) } returns ISSUE

        val searchIssueListRequest = SearchIssueListRequest(
            topic = "title",
            content = "title",
            asc = true,
            orderBy = "createdAt",
            page = 0,
            pageSize = 5
        )

        val pageable = PageRequest.of(searchIssueListRequest.page, searchIssueListRequest.pageSize)

        every { memberService.searchEmail(any()) } returns MEMBER
        every { teamService.getTeamById(any()) } returns TEAM
        every { issueRepository.findAllIssueList(any(),any(),any(),any(),any(),any()) } returns PageImpl(listOf(ISSUE, ISSUE, ISSUE, ISSUE, ISSUE, ISSUE))

        val result = issueService.getIssueList("test@test.com", searchIssueListRequest, pageable)


        result.size shouldBe 6
        result.first().title shouldBe "title"
    }

    @Test
    fun `이슈를 업데이트 할 경우 정상적으로 업데이트가 되는지 확인`(){

        val issueUpdateRequest = IssueUpdateRequest(
            "title2",
            "description2",
        )

        every { issueRepository.findByIdOrNull(any()) } answers  {
            ISSUE.update(issueUpdateRequest)
            ISSUE
        }

        every { issueRepository.save(any()) } returns ISSUE

        val result = issueService.updateIssue("test@test.com", 1L, issueUpdateRequest, null)

        result shouldBe "업데이트 가 완료 되었습니다"
        ISSUE.getTitle() shouldBe "title2"
        ISSUE.getDescription() shouldBe "description2"

    }


    companion object{
        private val TEAM = Team(
            "testTeam",
            listOf(),
            listOf()
        )

        val MEMBER = Member(
            "test",
            "test@test.com",
            "password",
            Role.USER,
            TEAM
        )

        val ISSUE = Issue(
            "title",
            "description",
            Priority.DANGER,
            WorkingStatus.DONE,
            MEMBER,
            TEAM,
            listOf(),
            null,
            1L
        )

        val ISSUE2 = Issue(
            "aaaa",
            "description",
            Priority.DANGER,
            WorkingStatus.DONE,
            MEMBER,
            TEAM,
            listOf(),
            null,
            1L
        )

        val DUMMY = Team(
            "dummy",
            listOf(),
            listOf()
        )
    }
}