package com.dedytech.springsecuritykotlin.auth

import lombok.Builder

@Builder
data class AuthenticationRequest(
    var email: String,
    var password: String
) {
}