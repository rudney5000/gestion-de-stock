package com.dedytech.springsecuritykotlin.auth

import com.dedytech.springsecuritykotlin.user.Role
import lombok.Builder

@Builder
data class RegisterRequest(
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
    var role: Role
) {
}