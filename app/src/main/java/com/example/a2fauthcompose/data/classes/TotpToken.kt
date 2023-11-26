package com.example.a2fauthcompose.data.classes

class TotpToken(
    password: String,
    val expiresAt: Long
) : AbstractToken(
    password
) {

    constructor(otp: OTP) : this(
        password = otp.password,
        expiresAt = otp.generated_at!!.toLong() + otp.period!!
    )

}