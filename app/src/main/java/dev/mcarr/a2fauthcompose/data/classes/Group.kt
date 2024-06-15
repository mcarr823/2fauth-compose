package dev.mcarr.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

/**
 * A group is a name given to two or more 2faccounts to group them together.
 * eg. You could have a group "Development" with accounts for Github and Bitbucket,
 * and another group "Social Media" for Facebook and Twitter.
 * */

/**
 * @param id ID of the Group
 * @param name Name of the Group
 * @param twofaccounts_count Number of 2FA accounts in the group
 * */
@Serializable
data class Group(
    val id: Int,
    val name: String,
    val twofaccounts_count: Int
)

/**
 * @param name Name of the Group
 * */
@Serializable
data class CreateGroupRequest(
    val name: String
)

/**
 * @param name Name of the Group
 * */
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