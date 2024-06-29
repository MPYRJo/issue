package com.yoong.myissue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
class MyissueApplication

fun main(args: Array<String>) {
    runApplication<MyissueApplication>(*args)
}



