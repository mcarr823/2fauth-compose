package dev.mcarr.a2fauthcompose.data.classes

import dev.mcarr.a2fauthcompose.data.entities.AccountEntity
import dev.mcarr.a2fauthcompose.viewmodels.AbstractTokenViewModel
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
abstract class AbstractAccount<T : AbstractToken>(
    val id: Int,
    val groupId: Int?,
    val service: String?,
    val account: String,
    val icon: String?,
    val digits: Int,
    val algorithm: String,
    val secret: String?
) {

    abstract fun generate(secret: ByteArray, isGoogleAuthenticator: Boolean): T

    fun generate(isGoogleAuthenticator: Boolean): T? {
        val secretBytes = secret?.toByteArray() ?: return null
        return this.generate(
            secret = secretBytes,
            isGoogleAuthenticator = isGoogleAuthenticator
        )
    }

    fun generate(): T? = generate(isGoogleAuthenticator = true)

    fun getAlgorithm(): HmacAlgorithm = when(algorithm){
        "sha1" -> HmacAlgorithm.SHA1
        "sha256" -> HmacAlgorithm.SHA256
        "sha512" -> HmacAlgorithm.SHA512
        else -> throw Exception("Unknown algorithm")
    }

    fun toViewModel() = AbstractTokenViewModel<T>(this)

    companion object{

        const val TYPE_TOTP = "totp"
        const val TYPE_HOTP = "hotp"
        const val TYPE_STEAM = "steamtotp"

    }

}