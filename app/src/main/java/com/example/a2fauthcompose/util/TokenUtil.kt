package com.example.a2fauthcompose.util

import com.example.a2fauthcompose.data.classes.AbstractAccount
import com.example.a2fauthcompose.data.classes.AbstractToken
import com.example.a2fauthcompose.data.classes.Account
import com.example.a2fauthcompose.data.classes.HotpToken
import com.example.a2fauthcompose.data.classes.OTP
import com.example.a2fauthcompose.data.classes.TotpToken
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import java.util.concurrent.TimeUnit

class TokenUtil(
    httpUtil: HttpUtil,
    private val useHttp: Boolean
) {

    private val api = Api(httpUtil)

    suspend fun getOtp(account: AbstractAccount): AbstractToken {

        if (useHttp) {
            val otp = api.getOtp(account.id)
            return when (otp.otp_type){
                AbstractToken.TYPE_TOTP -> TotpToken(otp)
                AbstractToken.TYPE_HOTP -> HotpToken(otp)
                else -> throw Exception("Unknown token")
            }
        }

        val secret = account.secret?.toByteArray() ?: throw Exception("No secret")

        // Hard-code this to true for now
        val isGoogleAuthenticator = true

        return account.generate(
            secret = secret,
            isGoogleAuthenticator = isGoogleAuthenticator
        )

    }

}