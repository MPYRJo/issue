package com.yoong.myissue.domain.member.controller

import com.yoong.myissue.domain.member.dto.LoginRequest
import com.yoong.myissue.domain.member.dto.LoginResponse
import com.yoong.myissue.domain.member.dto.SignupRequest
import com.yoong.myissue.domain.member.service.MemberServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberServiceImpl
) {

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(signupRequest))
    }


    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.login(loginRequest))

    }
}