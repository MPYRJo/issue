package com.yoong.myissue.infra.schedulier

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledTasks(
    private val issueRepository: IssueRepository
){

    @Scheduled(cron = "0 0 0 * * *")
    fun deletedObject(){
        issueRepository.deletedIssue()
    }
}