package dev.mcarr.a2fauthcompose.util

import dev.mcarr.a2fauthcompose.data.classes.AbstractAccount
import dev.mcarr.a2fauthcompose.data.classes.AbstractToken
import dev.mcarr.a2fauthcompose.data.classes.Account
import dev.mcarr.a2fauthcompose.data.classes.HotpToken
import dev.mcarr.a2fauthcompose.data.classes.OTP
import dev.mcarr.a2fauthcompose.data.classes.TotpToken
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import java.util.concurrent.TimeUnit

class TokenUtil(
    httpUtil: HttpUtil
) {

    private val api = Api(httpUtil)

    suspend fun getOtp(account: AbstractAccount): AbstractToken {

        if (account.secret != null){
            return account.generate() ?: throw Exception("Failed to generate")
        } else {
            val otp = api.getOtp(account.id)
            return when (otp.otp_type){
                AbstractToken.TYPE_TOTP -> TotpToken(otp)
                AbstractToken.TYPE_HOTP -> HotpToken(otp)
                else -> throw Exception("Unknown token")
            }
        }

    }

}