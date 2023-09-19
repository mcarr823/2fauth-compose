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



    //twofaccounts

    // Get all 2FA accounts
    // Find all 2FA accounts of the authenticated user
    suspend fun getAll2FaAccounts(withSecret: Boolean): List<Account> =
        get("/api/v1/twofaccounts?withSecret=$withSecret").body()

    // Create 2FA account
    // Creates a new 2FA account for the authenticated user
    suspend fun create2FaAccount(req: CreateAccountRequest): Account =
        post("/api/v1/twofaccounts"){
            setBody(req)
        }.body()

    // Mass delete 2FA accounts
    // Mass deletes 2FA accounts of the authenticated user matching the IDs passed as query parameter.
    suspend fun delete2FaAccount(ids: IntArray): Boolean =
        delete("/api/v1/twofaccounts?ids="+ids.joinToString(","))

    // Find 2FA account by ID
    // Returns a single 2FA account of the authenticated user
    suspend fun get2FaAccount(id: Int, withSecret: Boolean): Account =
        get("/api/v1/twofaccounts/$id?withSecret=$withSecret").body()

    // Update 2FA account
    // Updates a 2FA account of the authenticated user
    suspend fun update2FaAccount(id: Int, req: UpdateAccountRequest): Account =
        put(" /api/v1/twofaccounts/$id"){
            setBody(req)
        }.body()

    // Delete 2FA account
    // Deletes a 2FA account of the authenticated user
    suspend fun delete2FaAccount(id: Int): Boolean =
        delete(" /api/v1/twofaccounts/$id")

    // Not implemented:
    //  /api/v1/twofaccounts/migration
    //  /api/v1/twofaccounts/preview
    //  /api/v1/twofaccounts/reorder
    //  /api/v1/twofaccounts/withdraw
    //  /api/v1/twofaccounts/export
    //  /api/v1/groups/{id}/twofaccounts
}