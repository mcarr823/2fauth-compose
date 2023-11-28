package com.example.a2fauthcompose.data.classes

import com.example.a2fauthcompose.data.entities.AccountEntity
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm

/**
 * @param id ID of the 2FA account
 * @param groupId The ID of the group the 2FA account belongs to
 * @param service The Issuer of the 2FA account
 * @param account The Label of the 2FA account
 * @param icon The filename of the icon which decorate the 2FA account
 * @param secret A base32 encoded string used by the cryptographic algorithm
 * to generate the One-Time Password.
 * @param digits The number of digits of the generated One-Time Password
 * @param algorithm The algorithm used to generate the One-Time Password.
 * sha1, sha256, sha512, or md5
 **/
abstract class AbstractAccount(
    val id: Int,
    val groupId: Int?,
    val service: String?,
    val account: String,
    val icon: String?,
    val digits: Int,
    val algorithm: String,
    val secret: String?
) {

    abstract fun generate(secret: ByteArray): AbstractToken

    fun getAlgorithm(): HmacAlgorithm = when(algorithm){
        "sha1" -> HmacAlgorithm.SHA1
        "sha256" -> HmacAlgorithm.SHA256
        "sha512" -> HmacAlgorithm.SHA512
        else -> throw Exception("Unknown algorithm")
    }

    companion object{

        const val TYPE_TOTP = "totp"
        const val TYPE_HOTP = "hotp"

        fun parse(a: AccountEntity): AbstractAccount = when(a.otp_type){
            TYPE_TOTP -> TotpAccount(a)
            TYPE_HOTP -> HotpAccount(a)
            else -> throw Exception("Unknown account type")
        }

    }

}