package dev.mcarr.a2fauthcompose.data.classes

import android.util.Log
import java.util.concurrent.TimeUnit

class TotpToken(
    password: String,
    val expiresAt: Long,
    val period: Int
) : AbstractToken(
    password
) {

    constructor(otp: OTP) : this(
        password = otp.password,
        expiresAt = calculateExpiryTime(otp),
        period = otp.period!!
    )

    /**
     * @return How much longer, in milliseconds, the token will
     * be valid for.
     * */
    fun millisUntilExpired(): Long {
        val now = System.currentTimeMillis()
        val expiredMillis = TimeUnit.SECONDS.toMillis(expiresAt)
        return expiredMillis - now
    }

    companion object{

        /**
         * Calculates the time at which the token will expire.
         *
         * @return When the token will expire, calculated as
         * seconds since epoch.
         * */
        private fun calculateExpiryTime(otp: OTP): Long {

            // Assume the generated_at and period fields are non-null,
            // since all TOTP tokens should have these.
            val generatedAt = otp.generated_at!!.toLong()
            val period = otp.period!!

            // Calculate the period start based on the token generation
            // time and the length of the period.
            val remainder = generatedAt.rem(period)
            val periodStart = generatedAt - remainder

            val periodFinish = periodStart + period
            Log.i("TotpToken", "Generated: $generatedAt, expires: $periodFinish")
            return periodFinish
        }

    }

}