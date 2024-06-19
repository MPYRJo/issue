package com.yoong.myissue.domain.comment.service

import com.yoong.myissue.domain.comment.dto.CreateCommentRequest
import com.yoong.myissue.domain.comment.dto.UpdateCommentRequest
import com.yoong.myissue.infra.dto.UpdateResponse
import org.springframework.stereotype.Service

@Service
class CommentService {

    fun createComment(createCommentRequest: CreateCommentRequest): String {
        //TODO("Comment에 해당 하는 이슈가 존재 하지 않을 경우 ModelNotfoundException")
        //TODO("Comment 등록")
        TODO("등록 된 코맨트를 반환")
    }

    fun updateComment(commentId: Long, updateCommentRequest: UpdateCommentRequest): UpdateResponse {
        //TODO("Comment에 해당 하는 id가 존재 하지 않을 경우 ModelNotfoundException")
        //TODO("Comment 업데이트")
        TODO("업데이트 된 코맨트를 반환")
    }

    fun deleteComment(commentId: Long): String {
        //TODO("Comment에 해당 하는 id가 존재 하지 않을 경우 ModelNotfoundException")
        //TODO("Comment 삭제")
        TODO("삭제 여부를 반환")
    }


}
