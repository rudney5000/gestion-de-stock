package com.dedytech.springsecuritykotlin.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/demo-controller")
class DemoController {
    @GetMapping
    fun syHello(): ResponseEntity<String> = ResponseEntity.ok("Hello from secured endpoint")
}