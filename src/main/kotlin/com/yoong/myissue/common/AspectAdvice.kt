package com.yoong.myissue.common

import com.yoong.myissue.common.annotationGather.CheckAuthentication
import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.domain.issue.enum.Role
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.domain.team.service.DUMMY_TEAM
import com.yoong.myissue.domain.team.service.ExternalTeamService
import com.yoong.myissue.exception.clazz.DummyTeamException
import com.yoong.myissue.exception.clazz.DuplicatedModelException
import com.yoong.myissue.exception.clazz.IllegalArgumentException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class AspectAdvice(
    private val validAuthentication: ValidAuthentication,
    private val memberService: ExternalMemberService,
    private val teamService: ExternalTeamService
) {

    @Before("@annotation(com.yoong.myissue.common.annotationGather.CheckAuthentication)")
    fun checkAuthentication(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val annotation = method.getAnnotation(CheckAuthentication::class.java)

        val authenticationType = annotation.authenticationType

        val member = memberService.searchEmail(joinPoint.args[1].toString())

        validAuthentication.role(member.getRole(), authenticationType)
    }

    @Before("@annotation(com.yoong.myissue.common.annotationGather.CheckDummyTeam)")
    fun checkDummyTeam(joinPoint: JoinPoint) {

        val teamId = joinPoint.args[0] as Long

        if (teamId == DUMMY_TEAM) throw DummyTeamException()
    }

    @Before("@annotation(com.yoong.myissue.common.annotationGather.LeaderChoosesOtherTeam)")
    fun leaderChoosesOtherTeam(joinPoint: JoinPoint) {

        val teamId = joinPoint.args[0] as Long
        val memberEmail = joinPoint.args[1] as String

        val team = teamService.getTeamById(teamId)
        val member = memberService.searchEmail(memberEmail)

        if(member.getRole() == Role.LEADER && team != member.getTeam()) throw IllegalArgumentException("본인 소속 외에 다른 팀은 선택할 수 없습니다")

    }

    @Before("@annotation(com.yoong.myissue.common.annotationGather.CheckUser)")
    fun checkUser(joinPoint: JoinPoint){
        val memberId = joinPoint.args[0] as Long
        val member = memberService.searchId(memberId)

        if(member.getRole() != Role.USER) throw IllegalArgumentException("리더 및 관리자는 팀원으로 추가 및 방출할 수 없습니다")
    }

    @Before("@annotation(com.yoong.myissue.common.annotationGather.CheckMine)")
    fun checkMine(joinPoint: JoinPoint){
        val memberId = joinPoint.args[0] as Long
        val leaderEmail = joinPoint.args[1] as String
        val member = memberService.searchId(memberId)
        val leader = memberService.searchEmail(leaderEmail)

        if(member == leader) throw DuplicatedModelException("맴버", "나 자신을 선택할 수 없습니다")
    }



}