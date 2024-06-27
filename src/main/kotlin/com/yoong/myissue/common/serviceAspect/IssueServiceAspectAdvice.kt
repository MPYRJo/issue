package com.yoong.myissue.common.serviceAspect

import com.yoong.myissue.domain.issue.enumGather.Role
import com.yoong.myissue.domain.issue.service.ExternalIssueService
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class IssueServiceAspectAdvice(
    private val memberService: ExternalMemberService,
    private val issueService: ExternalIssueService
){

    @Before("@annotation(com.yoong.myissue.common.annotationGather.CheckMyTeam)")
    fun checkMyTeam(joinPoint: JoinPoint){
        val member = memberService.searchEmail(joinPoint.args[0] as String)
        val issue = issueService.getIssue(joinPoint.args[1] as Long)

        if(member.getRole() != Role.ADMIN && member.getTeam() != issue.getTeam()) throw IllegalArgumentException("다른 팀 정보는 선택 할 수 없습니다")
    }
}