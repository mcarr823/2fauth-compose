package com.example.a2fauthcompose.util

import com.example.a2fauthcompose.data.classes.Account
import com.example.a2fauthcompose.data.classes.AssignGroupRequest
import com.example.a2fauthcompose.data.classes.CreateAccountRequest
import com.example.a2fauthcompose.data.classes.CreateGroupRequest
import com.example.a2fauthcompose.data.classes.CreateIconResponse
import com.example.a2fauthcompose.data.classes.CreateOtpRequest
import com.example.a2fauthcompose.data.classes.CreateSettingRequest
import com.example.a2fauthcompose.data.classes.Group
import com.example.a2fauthcompose.data.classes.OTP
import com.example.a2fauthcompose.data.classes.QrCode
import com.example.a2fauthcompose.data.classes.QrCodeUrl
import com.example.a2fauthcompose.data.classes.Setting
import com.example.a2fauthcompose.data.classes.UpdateAccountRequest
import com.example.a2fauthcompose.data.classes.UpdateGroupRequest
import com.example.a2fauthcompose.data.classes.UpdateSettingRequest
import com.example.a2fauthcompose.data.classes.UpdateUserPreferenceRequest
import com.example.a2fauthcompose.data.classes.UserPreference
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

/**
 * Implementation of the 2FAuth REST API v1.1.0
 * https://docs.2fauth.app/api/
 **/
class Api(
    val httpUtil: HttpUtil
) {

    private suspend fun get(path: String) =
        httpUtil.get(path)

    private suspend fun post(path: String, setBodyCallback: HttpRequestBuilder.() -> Unit) =
        httpUtil.post(path, setBodyCallback)

    private suspend fun put(path: String, setBodyCallback: HttpRequestBuilder.() -> Unit) =
        httpUtil.put(path, setBodyCallback)

    private suspend fun delete(path: String) =
        httpUtil.delete(path)
}