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


/**
 * @param id ID of the 2FA account
 * @param group_id The ID of the group the 2FA account belongs to
 * @param service The Issuer of the 2FA account
 * @param account The Label of the 2FA account
 * @param icon The filename of the icon which decorate the 2FA account
 * @param otp_type The type of 2FA account. Either "totp" or "hotp"
 * @param secret A base32 encoded string used by the cryptographic algorithm
 * to generate the One-Time Password.
 * @param digits The number of digits of the generated One-Time Password
 * @param algorithm The algorithm used to generate the One-Time Password.
 * sha1, sha256, sha512, or md5
 * @param period For TOTP only. The validity duration of One-Time Password
 * generated for the account
 * @param counter For HOTP only. The value of the counter used to synchronize
 * the account with its verification servers
 **/
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

//Account and type are the only 2 required fields
/**
 * @param service The Issuer of the 2FA account
 * @param account The Label of the 2FA account
 * @param icon The filename of the icon which decorate the 2FA account
 * @param otp_type The type of 2FA account. Either "totp" or "hotp"
 * @param secret A base32 encoded string used by the cryptographic algorithm to
 * generate the One-Time Password.
 * @param digits The number of digits of the generated One-Time Password
 * @param algorithm The algorithm used to generate the One-Time Password.
 * sha1, sha256, sha512, or md5
 * @param period For TOTP only. The validity duration of One-Time Password
 * generated for the account
 * @param counter For HOTP only. The value of the counter used to synchronize
 * the account with its verification servers
 **/
@Serializable
data class CreateAccountRequest(
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

/**
 * @param service The Issuer of the 2FA account
 * @param account The Label of the 2FA account
 * @param icon The filename of the icon which decorate the 2FA account
 * @param otp_type The type of 2FA account. Either "totp" or "hotp"
 * @param secret A base32 encoded string used by the cryptographic algorithm to
 * generate the One-Time Password.
 * @param digits The number of digits of the generated One-Time Password
 * @param algorithm The algorithm used to generate the One-Time Password.
 * sha1, sha256, sha512, or md5
 * @param period For TOTP only. The validity duration of One-Time Password
 * generated for the account
 * @param counter For HOTP only. The value of the counter used to synchronize
 * the account with its verification servers
 **/
@Serializable
data class UpdateAccountRequest(
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