package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

@Serializable
class OTP(
    password: String,
    otp_type: String,
    generated_at: Int?,
    period: Int?,
    counter: Int?
)

@Serializable
data class CreateOtpRequest(
    val service: String,
    val account: String,
    val icon: String, //filename
    val otp_type: String,
    val digits: Int,
    val algorithm: String,
    val secret: String?,
    val period: Int?,
    val counter: Int?,
)