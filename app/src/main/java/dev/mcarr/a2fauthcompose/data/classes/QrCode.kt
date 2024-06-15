package dev.mcarr.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

/**
 * Represents a QR code image, which should (but won't necessarily) contain an otpauth URL.
 * @param qrcode Base64-encoded image of a QR code
 * */
@Serializable
data class QrCode(
    val qrcode: String
)

/**
 * Represents a URL from a decoded QR code.
 * Expected to be an otpauth URL, but in reality it could be any URL.
 * @param data The URL extracted from a decoded QR code
 * */
@Serializable
data class QrCodeUrl(
    val data: String
)