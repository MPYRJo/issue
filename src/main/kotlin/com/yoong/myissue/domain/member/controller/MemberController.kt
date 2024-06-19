package com.yoong.myissue.domain.member.controller

import com.yoong.myissue.domain.member.dto.SignupRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController {

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(signupRequest))
    }
}