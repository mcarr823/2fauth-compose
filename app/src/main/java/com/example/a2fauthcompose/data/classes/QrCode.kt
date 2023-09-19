package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class QrCode(
    val qrcode: String
)

@Serializable
data class QrCodeUrl(
    val data: String
)