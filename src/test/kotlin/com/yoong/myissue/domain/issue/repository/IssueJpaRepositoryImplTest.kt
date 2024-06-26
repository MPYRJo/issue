package com.yoong.myissue.domain.issue.repository

import com.yoong.myissue.domain.comment.entity.Comment
import com.yoong.myissue.domain.comment.enumGather.CheckingStatus
import com.yoong.myissue.domain.issue.entity.Issue
import com.yoong.myissue.domain.issue.enumGather.Priority
import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.issue.enumGather.WorkingStatus
import com.yoong.myissue.domain.member.entity.Member
import com.yoong.myissue.domain.team.entity.Team
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
//@Import(value = [QueryDslSupport::class])
class IssueJpaRepositoryImplTest @Autowired constructor(
    val issueRepository: IssueJpaRepository,
) {


    @Test
    fun `findAll 에 아무것도 입력하지 않았을 경우 제대로 출력하는 지 확인`(){

        issueRepository.saveAll(DEFAULT_ISSUE_LIST)
        issueRepository.flush()

        val result = issueRepository.findAll()

        result.size shouldBe 12

    }



    companion object {
        private val TEAM = Team(name = "teatTeam")
        private val MEMBER = Member("test", "test", "test", Role.USER, TEAM)
        private val ISSUE =Issue(
            title = "가나다",
            description = "",
            priority = Priority.DANGER,
            workingStatus = WorkingStatus.DONE,
            member = MEMBER,
            team = TEAM,
            listOf()
        )
        private val COMMENT = Comment(
            checkingStatus = CheckingStatus.DONE,
            content = "",
            member = MEMBER,
            issue = ISSUE,
        )
        private val DEFAULT_ISSUE_LIST = listOf(
            ISSUE,
            Issue(
                title = "나다라",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf(COMMENT)
            ),
            Issue(
                title = "디리미",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "마바사",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "사아자",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "아자차",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "자차카",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "차카타",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "카타파",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "타파하",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "파하가",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            ),
            Issue(
                title = "하가나",
                description = "",
                priority = Priority.DANGER,
                workingStatus = WorkingStatus.DONE,
                member = MEMBER,
                team = TEAM,
                listOf()
            )
        )
    }

}