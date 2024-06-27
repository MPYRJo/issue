package com.yoong.myissue.common.serviceAspect

import com.yoong.myissue.common.annotationGather.CheckAuthentication
import com.yoong.myissue.common.annotationGather.CheckMyComment
import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.domain.comment.repository.CommentRepository
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.exception.clazz.BadRequestException
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class CommentServiceAspectAdvice(
    private val memberService: ExternalMemberService,
    private val commentRepository: CommentRepository,
    private val validAuthentication: ValidAuthentication
){

    @Before("@annotation(com.yoong.myissue.common.annotationGather.CheckMyComment)")
    fun checkMyComment(joinPoint: JoinPoint){

        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val annotation = method.getAnnotation(CheckMyComment::class.java)

        val crudType = annotation.crudType

        val member = memberService.searchEmail(joinPoint.args[1] as String)

        val comment = commentRepository.findByIdOrNull(joinPoint.args[0] as Long) ?: throw ModelNotFoundException("댓글", joinPoint.args[0] as String)

        if(!validAuthentication.isSame(member, comment)) throw BadRequestException("다른 사람의 댓글은 ${crudType}할 수 없습니다")
    }
}