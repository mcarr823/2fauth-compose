package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val key: String,
    @Contextual
    val value: Any
)

@Serializable
data class UpdateUserPreferenceRequest(
    @Contextual
    val value: Any
)