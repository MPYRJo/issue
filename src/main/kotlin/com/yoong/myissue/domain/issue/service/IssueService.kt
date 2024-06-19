package com.yoong.myissue.domain.issue.service

import com.yoong.myissue.domain.issue.dto.IssueCreateRequest
import com.yoong.myissue.domain.issue.dto.IssueResponse
import com.yoong.myissue.domain.issue.dto.IssueUpdateRequest
import com.yoong.myissue.domain.issue.dto.IssueUpdateResponse
import org.springframework.stereotype.Service

@Service
class IssueService {

    fun createIssue(issueCreateRequest: IssueCreateRequest): String {

        TODO("이슈 내용을 받아서 저장")
    }

    fun getIssue(issueId: Long): IssueResponse {
        TODO("이슈 아이디 를 받이서 아이디 가 없는 경우 ModelNotFoundException")
        //TODO(이슈를 조회 해서 반환)
    }

    fun getIssueList(): List<IssueResponse> {
        //TODO(검색 결과가 들어 왔을 경우 검색 결과에 맞춰서 쿼리를 내려 준다)
        TODO("쿼리에 맞는 값이 나왔다면 List 형태로 반환")
    }

    fun updateIssue(issueId: Long, issueUpdateRequest: IssueUpdateRequest): IssueUpdateResponse {
        //TODO("이슈 아이디 를 받이서 아이디 가 없는 경우 ModelNotFoundException")
        //TODO("가져온 이슈를 업데이트")
        TODO("이슈 변경 사항을 반환")
    }

    fun deleteIssue(issueId: Long): String {
        //TODO("이슈 아이디 를 받이서 아이디 가 없는 경우 ModelNotFoundException")
        //TODO("가져온 이슈를 SoftDelete")
        //TODO("가져온 이슈를 SpringScheduler 에 의해서 3시간(90일) 이후에 자동 삭제")
        TODO("삭제 완료 내용을 반환")
    }


}
