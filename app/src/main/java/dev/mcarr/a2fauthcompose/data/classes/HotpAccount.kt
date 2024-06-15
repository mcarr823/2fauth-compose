package dev.mcarr.a2fauthcompose.data.classes

import dev.mcarr.a2fauthcompose.data.entities.AccountEntity
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator

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
 * @param counter For HOTP only. The value of the counter used to synchronize
 * the account with its verification servers
 **/
class HotpAccount(
    id: Int,
    groupId: Int?,
    service: String?,
    account: String,
    icon: String?, //filename
    digits: Int,
    algorithm: String,
    secret: String?,
    val counter: Int
) : AbstractAccount(
    id, groupId, service, account, icon, digits, algorithm, secret
) {

    constructor(a: Account) : this(
        id = a.id,
        groupId = a.group_id,
        service = a.service,
        account = a.account,
        icon = a.icon,
        digits = a.digits,
        algorithm = a.algorithm,
        secret = a.secret,
        counter = a.counter!!
    )

    constructor(a: AccountEntity) : this(
        id = a.id,
        groupId = a.group_id,
        service = a.service,
        account = a.account,
        icon = a.icon,
        digits = a.digits,
        algorithm = a.algorithm,
        secret = a.secret,
        counter = a.counter!!
    )

    override fun generate(secret: ByteArray, isGoogleAuthenticator: Boolean): HotpToken {
        val config = HmacOneTimePasswordConfig(
            codeDigits = digits,
            hmacAlgorithm = getAlgorithm()
        )
        val hmacOneTimePasswordGenerator = HmacOneTimePasswordGenerator(
            secret,
            config
        )
        val password = hmacOneTimePasswordGenerator.generate(counter.toLong())
        return HotpToken(password = password)
        //TODO: increment counter
    }

}