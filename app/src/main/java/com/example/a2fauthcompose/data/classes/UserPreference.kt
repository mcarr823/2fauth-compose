package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class UserPreference(
    val key: String,
    val value: Any
){

    constructor(json: JsonObject) : this(
        json["key"].toString(),
        json["value"]!!
    )

    companion object{
        fun parseMulti(jsonArray: JsonArray): List<UserPreference> =
            jsonArray.map(JsonElement::jsonObject).map(::UserPreference)
    }

}

@Serializable
data class UpdateUserPreferenceRequest(
    @Contextual
    val value: Any
)