package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val key: String,
    val value: Any
)

@Serializable
data class UpdateUserPreferenceRequest(
    val value: Any
)