package com.yoong.myissue.domain.comment.service

import com.yoong.myissue.common.clazz.ValidAuthentication
import com.yoong.myissue.common.enumGather.AuthenticationType
import com.yoong.myissue.domain.comment.dto.CreateCommentRequest
import com.yoong.myissue.domain.comment.dto.UpdateCommentRequest
import com.yoong.myissue.domain.comment.entity.Comment
import com.yoong.myissue.domain.comment.repository.CommentRepository
import com.yoong.myissue.domain.issue.service.ExternalIssueService
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.exception.clazz.BadRequestException
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueService: ExternalIssueService,
    private val memberService: ExternalMemberService,
    private val validAuthentication: ValidAuthentication
    ){

    fun createComment(email: String, createCommentRequest: CreateCommentRequest): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ALL)

        val issue = issueService.getIssue(createCommentRequest.issueId)

        commentRepository.save(
            Comment(
                content = createCommentRequest.content,
                checkingStatus = createCommentRequest.checkingStatus,
                member = member,
                issue = issue
            )
        )

        return "댓글이 등록 되었습니다 이슈 번호 : ${createCommentRequest.issueId}, 코맨트 내용 : ${createCommentRequest.content}"
    }

    fun updateComment(email: String, commentId: Long, updateCommentRequest: UpdateCommentRequest): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ALL)

        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("댓글", commentId.toString())

        if(!validAuthentication.isSame(member, comment)) throw BadRequestException("다른 사람의 댓글은 수정 할 수 없습니다")

        comment.update(updateCommentRequest)

        commentRepository.save(comment)

        return "댓글 수정 완료 했습니다"
    }

    fun deleteComment(email: String, commentId: Long): String {

        val member = memberService.searchEmail(email)

        validAuthentication.role(member.getRole(), AuthenticationType.ALL)

        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("댓글", commentId.toString())

        if(!validAuthentication.isSame(member, comment)) throw BadRequestException("다른 사람의 댓글은 삭제 할 수 없습니다")

        commentRepository.delete(comment)

        return "삭제가 완료 되었습니다 댓글 아이디 : $commentId"
    }


}
