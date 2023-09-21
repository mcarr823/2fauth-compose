package com.example.a2fauthcompose.data.classes

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class Setting(
    val key: String,
    val value: Any
){

    constructor(json: JsonObject) : this(
        json["key"].toString(),
        json["value"]!!
    )

    companion object{
        fun parseMulti(jsonArray: JsonArray): List<Setting> =
            jsonArray.map(JsonElement::jsonObject).map(::Setting)
    }

}

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