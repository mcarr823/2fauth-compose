package com.example.a2fauthcompose.data.classes

class HotpToken(
    password: String
) : AbstractToken(
    password
) {

    constructor(otp: OTP) : this(otp.password)

}