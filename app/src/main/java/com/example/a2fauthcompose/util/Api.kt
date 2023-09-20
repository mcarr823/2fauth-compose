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

    constructor(
        apiUrl: String,
        token: String,
        disableHttpsVerification: Boolean = false,
        testing: Boolean = false
    ) : this(HttpUtil(apiUrl, token, disableHttpsVerification, testing))

    private suspend fun get(path: String) =
        httpUtil.get(path)

    private suspend fun post(path: String, setBodyCallback: HttpRequestBuilder.() -> Unit) =
        httpUtil.post(path, setBodyCallback)

    private suspend fun put(path: String, setBodyCallback: HttpRequestBuilder.() -> Unit) =
        httpUtil.put(path, setBodyCallback)

    private suspend fun delete(path: String) =
        httpUtil.delete(path)



    //twofaccounts

    /**
     * Find all 2FA accounts of the authenticated user
     * @param withSecret If true, include the secret of the 2fa credential in the response
     * @return List of 2fa accounts
     * */
    suspend fun getAll2FaAccounts(withSecret: Boolean): List<Account> =
        get("/api/v1/twofaccounts?withSecret=$withSecret").body()

     /**
      * Creates a new 2FA account for the authenticated user
      * @param req Account creation request object for the newly added service
      * @return 2fa account which was created
      * */
    suspend fun create2FaAccount(req: CreateAccountRequest): Account =
        post("/api/v1/twofaccounts"){
            setBody(req)
        }.body()

    /**
     * Mass deletes 2FA accounts of the authenticated user matching the IDs passed as query parameter.
     * @param ids Array of ids for accounts to delete
     * @return True if successful
     * */
    suspend fun delete2FaAccount(ids: IntArray): Boolean =
        delete("/api/v1/twofaccounts?ids="+ids.joinToString(","))

    /**
     * Returns a single 2FA account of the authenticated user
     * @param id ID of the account to retrieve
     * @param withSecret If true, include the secret of the 2fa credential in the response
     * @return 2fa account with the given ID
     * */
    suspend fun get2FaAccount(id: Int, withSecret: Boolean): Account =
        get("/api/v1/twofaccounts/$id?withSecret=$withSecret").body()

    /**
     * Updates a 2FA account of the authenticated user
     * @param id ID of the account to update
     * @param req Updated account details to apply
     * @return Updated 2fa account
     * */
    suspend fun update2FaAccount(id: Int, req: UpdateAccountRequest): Account =
        put(" /api/v1/twofaccounts/$id"){
            setBody(req)
        }.body()

    /**
     * Deletes a 2FA account of the authenticated user
     * @param id ID of the account to delete
     * @return True if successful
     * */
    suspend fun delete2FaAccount(id: Int): Boolean =
        delete(" /api/v1/twofaccounts/$id")

    // Not implemented:
    //  /api/v1/twofaccounts/migration
    //  /api/v1/twofaccounts/preview
    //  /api/v1/twofaccounts/reorder
    //  /api/v1/twofaccounts/withdraw
    //  /api/v1/twofaccounts/export
    //  /api/v1/groups/{id}/twofaccounts


    // one-time password

    // Get a One-Time password
    // Returns a fresh One-Time Password for the 2FA account of the authenticated user matching the specified ID
    suspend fun getOtp(id: Int): OTP =
        get("/api/v1/twofaccounts/$id/otp").body()

    // Get a One-Time password
    // Returns a fresh One-Time password with related parameters for account defined by the provided data
    suspend fun getOtp(req: CreateOtpRequest): OTP =
        post("/api/v1/twofaccounts/otp"){
            setBody(req)
        }.body()


    // groups

    // Get all groups
    // Find all groups of the authenticated user
    suspend fun getAllGroups(): List<Group> =
        get("/api/v1/groups").body()

    // Create Group
    // Creates a new group for the authenticated user
    suspend fun createGroup(req: CreateGroupRequest): Group =
        post("/api/v1/groups"){
            setBody(req)
        }.body()

    // Find group by ID
    // Returns a single group of the authenticated user
    suspend fun getGroup(id: Int): Group =
        get("/api/v1/groups/$id").body()

    // Update group
    // Updates a group of the authenticated user
    suspend fun updateGroup(id: Int, req: UpdateGroupRequest): Group =
        post("/api/v1/groups/$id"){
            setBody(req)
        }.body()

    // Delete group
    // Deletes a group of the authenticated user. This will not delete any assigned 2FA accounts.
    suspend fun deleteGroup(id: Int): Boolean =
        delete("/api/v1/groups/$id")

    // Add 2FA accounts to a group
    // Adds a list of 2FA accounts to a group of the authenticated user. An account previously
    // assigned to another group will be removed from its former group. The 2FA accounts must be
    // owned by the authenticated user.
    suspend fun assignGroup(groupId: Int, req: AssignGroupRequest): Group =
        post("/api/v1/groups/$groupId/assign"){
            setBody(req)
        }.body()

    // Get all 2FA accounts of a group
    // Finds all existing 2FA accounts assigned to a group of the authenticated user
    suspend fun get2FaAccountsByGroupId(groupId: Int,  withSecret: Boolean): List<Account> =
        get("/api/v1/groups/$groupId/twofaccounts?withSecret=$withSecret").body()


    // icons

    // Upload an icon
    // Use this endpoint to upload and store an icon (an image file: jpeg, png, bmp, gif, svg, or webp) on the server
    suspend fun createIcon(file: File, mimetype: String): CreateIconResponse =
        post("/api/v1/icons"){
            setBody(formData {
                this.append("icon", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, mimetype)
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                })
            })
        }.body()

    // Delete an icon
    // Deletes an icon from the server
    suspend fun deleteIcon(filename: String): Boolean =
        delete("/api/v1/icons/$filename")


    // qr code

    // Encode a 2FA account in a QR code
    // Returns a QR code that represents a 2FA account owned by the authenticated user
    suspend fun getQrCode(id: Int): QrCode =
        get("/api/v1/twofaccounts/$id/qrcode").body()

    // Decode a QR code
    // Use this endpoint to decode a QR code (an image file: jpeg, png, bmp, gif, svg, or webp).
    // The QR code is expected to be a 2FA resource but any QR code will be decoded.
    suspend fun decodeQrCode(file: File, mimetype: String): QrCodeUrl =
        post("/api/v1/qrcode/decode"){
            setBody(formData {
                this.append("qrcode", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, mimetype)
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                })
            })
        }.body()


    // user preference

    // Get all user preferences
    // List all preferences of the authenticated user.
    suspend fun getAllUserPreferences(): List<UserPreference> =
        get("/api/v1/user/preferences").body()

    // Find user preference by name
    // Returns a single preference of the authenticated user
    suspend fun getUserPreference(key: String): UserPreference =
        get("/api/v1/user/preferences/$key").body()

    // Update user preference
    // Updates a preference of the authenticated user.
    suspend fun updateUserPreference(key: String, req: UpdateUserPreferenceRequest) =
        put("/api/v1/user/preferences/$key"){
            setBody(req)
        }


    // settings (admin only)

    // Get all settings
    // List all application settings. This includes 2FAuth native settings and possible additional
    // admin-defined settings.
    // Settings endpoints are restricted to user with an Administrator role.
    suspend fun getAllSettings(): List<Setting> =
        get("/api/v1/settings").body()

    // Create custom setting
    // Creates a new custom application setting. You are free to use this endpoint to store any
    // data you need to administrate your own app.
    // Settings endpoints are restricted to user with an Administrator role.
    suspend fun createSetting(req: CreateSettingRequest): Setting =
        post("/api/v1/settings"){
            setBody(req)
        }.body()

    // Find setting by name
    // Returns a single application setting, whether it is a native 2FAuth setting or a custom
    // setting.
    // Settings endpoints are restricted to user with an Administrator role.
    suspend fun getSetting(key: String): Setting =
        get("/api/v1/settings/$key").body()

    // Update setting
    // Updates an application setting, whether it is a native 2FAuth setting or a custom setting.
    // Will create the setting if it does not exist.
    // Settings endpoints are restricted to user with an Administrator role.
    suspend fun updateSetting(key: String, req: UpdateSettingRequest): Setting =
        put("/api/v1/settings/$key"){
            setBody(req)
        }.body()

    // Delete custom setting
    // Deletes a custom application setting.
    // Settings endpoints are restricted to user with an Administrator role.
    suspend fun deleteSetting(key: String): Boolean =
        delete("/api/v1/settings/$key")

}