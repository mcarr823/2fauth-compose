package dev.mcarr.a2fauthcompose.data.classes

import kotlinx.serialization.Serializable

/**
 * Icons are displayed next to each 2faccount to make it quick and easy to identify services.
 * Each icon must have a unique name, but can be used on more than one 2faccount.
 * eg. You could use atlassian.png for both your personal bitbucket account and your work account
 * and they would display the same icon.
 * But if you wanted to change only one of them, you would need to give the new icon a different
 * name, such as atlassian2.png instead.
 * */

/**
 * @param filename The icon filename
 * */
@Serializable
data class CreateIconResponse(
    val filename: String
)