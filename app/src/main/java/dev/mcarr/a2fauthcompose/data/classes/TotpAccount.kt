package dev.mcarr.a2fauthcompose.data.classes

import dev.mcarr.a2fauthcompose.data.entities.AccountEntity
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import java.util.concurrent.TimeUnit

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
 **/
class TotpAccount(
    id: Int,
    groupId: Int?,
    service: String?,
    account: String,
    icon: String?,
    digits: Int,
    algorithm: String,
    secret: String?,
    val period: Int
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
        period = a.period!!
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
        period = a.period!!
    )

    override fun generate(secret: ByteArray, isGoogleAuthenticator: Boolean): TotpToken {
        val config = TimeBasedOneTimePasswordConfig(
            codeDigits = digits,
            hmacAlgorithm = getAlgorithm(),
            timeStep = period.toLong(),
            timeStepUnit = TimeUnit.SECONDS
        )
        val timeBasedOneTimePasswordGenerator = TimeBasedOneTimePasswordGenerator(
            secret,
            config
        )
        val timestamp = System.currentTimeMillis()
        val password =
            if (isGoogleAuthenticator){
                GoogleAuthenticator(secret).generate()
            }else {
                timeBasedOneTimePasswordGenerator.generate()
            }
        val counter = timeBasedOneTimePasswordGenerator.counter()
        //The start of next time slot minus 1ms
        val endEpochMillis = timeBasedOneTimePasswordGenerator.timeslotStart(counter+1)-1
        val epochSeconds = TimeUnit.MILLISECONDS.toSeconds(endEpochMillis)
        return TotpToken(password = password, expiresAt = epochSeconds, period = period)
    }

}