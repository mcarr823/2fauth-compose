package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

@Serializable
class Setting(
    val key: String,
    val value: Any
)

@Serializable
class CreateSettingRequest(
    val key: String,
    val value: Any
)

@Serializable
class UpdateSettingRequest(
    val value: Any
)