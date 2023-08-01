package com.dedytech.springsecuritykotlin.auth

import lombok.Builder

@Builder
data class AuthenticationResponse(
    var token: String
)