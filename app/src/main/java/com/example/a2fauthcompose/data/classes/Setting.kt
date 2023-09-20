package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class Setting(
    val key: String,
    @Contextual
    val value: Any
)

@Serializable
class CreateSettingRequest(
    val key: String,
    @Contextual
    val value: Any
)

@Serializable
class UpdateSettingRequest(
    @Contextual
    val value: Any
)