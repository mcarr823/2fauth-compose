package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

/**
 * A group is a name given to two or more 2faccounts to group them together.
 * eg. You could have a group "Development" with accounts for Github and Bitbucket,
 * and another group "Social Media" for Facebook and Twitter.
 * */
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

/**
 * @param ids An array of 2faccount ids. These are the accounts you want to assign to a given group.
 * */
@Serializable
data class AssignGroupRequest(
    val ids: IntArray
)