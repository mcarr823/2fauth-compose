package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Int,
    val name: String,
    val twofaccounts_count: Int
)

@Serializable
data class CreateGroupRequest(
    val name: String
)

@Serializable
data class UpdateGroupRequest(
    val name: String
)

@Serializable
data class AssignGroupRequest(
    val ids: IntArray //otp ids
)