package com.example.a2fauthcompose.data.classes

abstract class AbstractToken(
    val password: String
) {

    companion object{
        const val TYPE_TOTP = "totp"
        const val TYPE_HOTP = "hotp"
    }

}