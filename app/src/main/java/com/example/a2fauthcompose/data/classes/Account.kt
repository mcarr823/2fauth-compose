package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Int,
    val group_id: Int?,
    val service: String?,
    val account: String,
    val icon: String?, //filename
    val otp_type: String,
    val digits: Int,
    val algorithm: String,
    val secret: String? = null,
    val period: Int?,
    val counter: Int?,
)

@Serializable
data class CreateAccountRequest(
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

@Serializable
data class UpdateAccountRequest(
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