package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

/**
 * Represents a 2fa account.
 * An "account" is a token generator for a given service.
 * It refers to the account on a third-party service for which you are generating tokens.
 * eg. an account for your Google login, an account for your Atlassian login, and so on.
 *
 * This is not to be confused with your 2fauth login.
 * */
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