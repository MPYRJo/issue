package com.yoong.myissue.common.annotation

import com.yoong.myissue.domain.team.service.DUMMY_TEAM

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckDummyTeam
