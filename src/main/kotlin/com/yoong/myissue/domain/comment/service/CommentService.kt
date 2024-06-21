package com.yoong.myissue.domain.comment.service

import com.yoong.myissue.domain.comment.dto.CreateCommentRequest
import com.yoong.myissue.domain.comment.dto.UpdateCommentRequest
import com.yoong.myissue.domain.comment.entity.Comment
import com.yoong.myissue.domain.comment.repository.CommentRepository
import com.yoong.myissue.domain.issue.service.ExternalIssueService
import com.yoong.myissue.domain.member.service.ExternalMemberService
import com.yoong.myissue.exception.`class`.ModelNotFoundException
import com.yoong.myissue.infra.dto.UpdateResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueService: ExternalIssueService,
    private val memberService: ExternalMemberService
){

    fun createComment(createCommentRequest: CreateCommentRequest, email: String): String {
        val member = memberService.searchEmail(email)
        val issue = issueService.getIssue(createCommentRequest.issueId)
        commentRepository.save(
            Comment(
                content = createCommentRequest.content,
                checkingStatus = createCommentRequest.checkingStatus,
                member = member,
                issue = issue
            )
        )
        return "코맨트가 등록 되었습니다 이슈 번호 : ${createCommentRequest.issueId}, 코맨트 내용 : ${createCommentRequest.content}"
    }

    fun updateComment(commentId: Long, updateCommentRequest: UpdateCommentRequest): UpdateResponse {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("댓글", commentId.toString())

        //TODO("Comment 업데이트")
        TODO("업데이트 된 코맨트를 반환")
    }

    fun deleteComment(commentId: Long): String {
        //TODO("Comment에 해당 하는 id가 존재 하지 않을 경우 ModelNotfoundException")
        //TODO("Comment 삭제")
        TODO("삭제 여부를 반환")
    }


}
