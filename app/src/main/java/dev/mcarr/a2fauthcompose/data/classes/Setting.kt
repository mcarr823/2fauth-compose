package dev.mcarr.a2fauthcompose.data.classes

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

/**
 * @param key Name of the setting. Max 128 chars
 * @param value Value of the setting. String, boolean, or integer
 * */
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

/**
 * @param key Name of the setting. Max 128 chars
 * @param value Value of the setting. String, boolean, or integer
 * */
@Serializable
class CreateSettingRequest(
    val key: String,
    @Contextual
    val value: Any
)

/**
 * @param value Value of the setting. String, boolean, or integer
 * */
@Serializable
class UpdateSettingRequest(
    @Contextual
    val value: Any
)