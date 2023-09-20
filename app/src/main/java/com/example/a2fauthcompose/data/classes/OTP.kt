package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class OTP(
    val password: String,
    val otp_type: String,
    val generated_at: Int?,
    val period: Int?,
    val counter: Int?=null
)

@Serializable
data class CreateOtpRequest(
    val service: String?,
    val account: String,
    val icon: String?, //filename
    val otp_type: String,
    val digits: Int?,
    val algorithm: String?,
    val secret: String?,
    val period: Int?,
    val counter: Int?,
)