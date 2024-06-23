package com.yoong.myissue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class MyissueApplication

fun main(args: Array<String>) {
    runApplication<MyissueApplication>(*args)
}



