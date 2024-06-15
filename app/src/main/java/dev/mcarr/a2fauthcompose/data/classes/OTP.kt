package dev.mcarr.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

/**
 * This class represents an OTP token as of a particular moment in time.
 * After generating a token, you will use the `password` (2FA token) value to prove your identity
 * to a third party.
 * */

/**
 * @param password The One-Time Password
 * @param otp_type The type of the One-Time Password. "totp" or "hotp"
 * @param generated_at TOTP only. The timestamp of the generation time
 * @param period TOTP only. The validity period of the password
 * @param counter HOTP only. The number of password computing iterations
 *
 * */
@Serializable
data class OTP(
    val password: String,
    val otp_type: String,
    val generated_at: Int?,
    val period: Int?,
    val counter: Int?=null
)

/**
 * @param service The Issuer of the 2FA account
 * @param account The Label of the 2FA account
 * @param icon The filename of the icon which decorate the 2FA account
 * @param otp_type The type of 2FA account. "totp" or "hotp"
 * @param secret A base32 encoded string used by the cryptographic algorithm to generate the
 * One-Time Password.
 * @param digits The number of digits of the generated One-Time Password
 * @param algorithm The algorithm used to generate the One-Time Password.
 * "sha1", "sha256", "sha512", or "md5"
 * @param period TOTP only.
 * The validity duration of One-Time Password generated for the account
 * @param counter HOTP only.
 * The value of the counter used to synchronize the account with its verification servers
 * */
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