package dev.mcarr.a2fauthcompose.util

import dev.mcarr.a2fauthcompose.data.classes.Account
import dev.mcarr.a2fauthcompose.data.classes.AssignGroupRequest
import dev.mcarr.a2fauthcompose.data.classes.CreateAccountRequest
import dev.mcarr.a2fauthcompose.data.classes.CreateGroupRequest
import dev.mcarr.a2fauthcompose.data.classes.CreateIconResponse
import dev.mcarr.a2fauthcompose.data.classes.CreateOtpRequest
import dev.mcarr.a2fauthcompose.data.classes.CreateSettingRequest
import dev.mcarr.a2fauthcompose.data.classes.Group
import dev.mcarr.a2fauthcompose.data.classes.OTP
import dev.mcarr.a2fauthcompose.data.classes.QrCode
import dev.mcarr.a2fauthcompose.data.classes.QrCodeUrl
import dev.mcarr.a2fauthcompose.data.classes.Setting
import dev.mcarr.a2fauthcompose.data.classes.UpdateAccountRequest
import dev.mcarr.a2fauthcompose.data.classes.UpdateGroupRequest
import dev.mcarr.a2fauthcompose.data.classes.UpdateSettingRequest
import dev.mcarr.a2fauthcompose.data.classes.UpdateUserPreferenceRequest
import dev.mcarr.a2fauthcompose.data.classes.UserPreference
import dev.mcarr.a2fauthcompose.viewmodels.SetupScreenViewModel
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import java.io.File

/**
 * Implementation of the 2FAuth REST API v1.1.0
 * https://docs.2fauth.app/api/
 **/
class Api(
    val httpUtil: HttpUtil
) {

    constructor(model: SetupScreenViewModel) : this(
        apiUrl = model.endpoint,
        token = model.token,
        httpsVerification = model.httpsVerification,
        debugMode = model.debugMode,
        storeSecrets = model.storeSecrets
    )

    constructor(
        apiUrl: String,
        token: String,
        httpsVerification: Boolean = true,
        debugMode: Boolean = false,
        storeSecrets: Boolean = false
    ) : this(
        httpUtil = HttpUtil(
            apiUrl = apiUrl,
            token = token,
            httpsVerification = httpsVerification,
            debugMode = debugMode,
            storeSecrets = storeSecrets
        )
    )

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
    suspend fun getAll2FaAccounts(withSecret: Boolean = httpUtil.storeSecrets): List<Account> =
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
    suspend fun get2FaAccount(id: Int, withSecret: Boolean = httpUtil.storeSecrets): Account =
        get("/api/v1/twofaccounts/$id?withSecret=$withSecret").body()

    /**
     * Updates a 2FA account of the authenticated user
     * @param id ID of the account to update
     * @param req Updated account details to apply
     * @return Updated 2fa account
     * */
    suspend fun update2FaAccount(id: Int, req: UpdateAccountRequest): Account =
        put("/api/v1/twofaccounts/$id"){
            setBody(req)
        }.body()

    /**
     * Deletes a 2FA account of the authenticated user
     * @param id ID of the account to delete
     * @return True if successful
     * */
    suspend fun delete2FaAccount(id: Int): Boolean =
        delete("/api/v1/twofaccounts/$id")

    // Not implemented:
    //  /api/v1/twofaccounts/migration
    //  /api/v1/twofaccounts/preview
    //  /api/v1/twofaccounts/reorder
    //  /api/v1/twofaccounts/withdraw
    //  /api/v1/twofaccounts/export
    //  /api/v1/groups/{id}/twofaccounts


    // one-time password

    /**
     * Returns a fresh One-Time Password for the 2FA account of the authenticated user matching the
     * specified ID
     * @param id ID of the 2faccount to retrieve a token for
     * @return Current OTP token for given account
     * */
    suspend fun getOtp(id: Int): OTP =
        get("/api/v1/twofaccounts/$id/otp").body()

    /**
     * Returns a fresh One-Time password with related parameters for account defined by the provided
     * data
     * @param req OTP creation request object
     * @return Current OTP token for `req` object passed in
     * */
    suspend fun getOtp(req: CreateOtpRequest): OTP =
        post("/api/v1/twofaccounts/otp"){
            setBody(req)
        }.body()


    // groups

    /**
     * Find all groups of the authenticated user
     * @return List of OTP account groups
     * */
    suspend fun getAllGroups(): List<Group> =
        get("/api/v1/groups").body()

    /**
     * Creates a new group for the authenticated user
     * @param req Group creation request object
     * @return Group created in the 2fauth system
     * */
    suspend fun createGroup(req: CreateGroupRequest): Group =
        post("/api/v1/groups"){
            setBody(req)
        }.body()

    /**
     * Returns a single group of the authenticated user
     * @param id ID of the group to retrieve
     * @return Group matching the given ID
     * */
    suspend fun getGroup(id: Int): Group =
        get("/api/v1/groups/$id").body()

    /**
     * Updates a group of the authenticated user
     * @param id ID of the group to update
     * @param req Group update request object
     * @return Updated group returned from the 2fauth system
     * */
    suspend fun updateGroup(id: Int, req: UpdateGroupRequest): Group =
        put("/api/v1/groups/$id"){
            setBody(req)
        }.body()

    /**
     * Deletes a group of the authenticated user. This will not delete any assigned 2FA accounts.
     * @param id ID of the group to delete
     * @return True if successful
     * */
    suspend fun deleteGroup(id: Int): Boolean =
        delete("/api/v1/groups/$id")

    /**
     * Adds a list of 2FA accounts to a group of the authenticated user. An account previously
     * assigned to another group will be removed from its former group. The 2FA accounts must be
     * owned by the authenticated user.
     * @param groupId ID of the group to assign to one or more OTP 2faccounts
     * @param req Assign group request object, containing the ids of the 2faccounts to assign to
     * the given group
     * @return The group which the given 2faccounts have been assigned to
     * */
    suspend fun assignGroup(groupId: Int, req: AssignGroupRequest): Group =
        post("/api/v1/groups/$groupId/assign"){
            setBody(req)
        }.body()

    /**
     * Finds all existing 2FA accounts assigned to a group of the authenticated user
     * @param groupId ID of the group for which to find assigned 2faccounts
     * @param withSecret If true, include account secrets in the response
     * @return List of accounts belonging to the specified group
     * */
    suspend fun get2FaAccountsByGroupId(groupId: Int, withSecret: Boolean = httpUtil.storeSecrets): List<Account> =
        get("/api/v1/groups/$groupId/twofaccounts?withSecret=$withSecret").body()


    // icons

    /**
     * Use this endpoint to upload and store an icon (an image file: jpeg, png, bmp, gif, svg, or webp) on the server
     * @param file Image file to upload
     * @param mimetype Mimetype of the image. eg. image/jpeg
     * @return Name of successfully uploaded file, wrapped in CreateIconResponse object
     * */
    suspend fun createIcon(file: File, mimetype: String): CreateIconResponse =
        post("/api/v1/icons"){
            setBody(formData {
                this.append("icon", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, mimetype)
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                })
            })
        }.body()

    /**
     * Deletes an icon from the server
     * @param filename Name of the icon to delete
     * @return True if successful
     * */
    suspend fun deleteIcon(filename: String): Boolean =
        delete("/api/v1/icons/$filename")


    // qr code

    /**
     * Returns a QR code that represents a 2FA account owned by the authenticated user
     * @param id ID of the 2faccount for which to generate a QR code
     * @return The generated QR code as a base64 image
     * */
    suspend fun getQrCode(id: Int): QrCode =
        get("/api/v1/twofaccounts/$id/qrcode").body()

    /**
     * Use this endpoint to decode a QR code (an image file: jpeg, png, bmp, gif, svg, or webp).
     * The QR code is expected to be a 2FA resource but any QR code will be decoded.
     * @param file QR code image file to decode
     * @param mimetype Mimetype of the image to decode from the file. eg. image/jpeg
     * @return URL which was encoded as the given QR code
     * */
    suspend fun decodeQrCode(file: File, mimetype: String): QrCodeUrl =
        decodeQrCode(file.readBytes(), mimetype)

    /**
     * Use this endpoint to decode a QR code (an image file: jpeg, png, bmp, gif, svg, or webp).
     * The QR code is expected to be a 2FA resource but any QR code will be decoded.
     * @param bytes ByteArray containing the image data of a QR code
     * @param mimetype Mimetype of the image to decode from the byte data. eg. image/jpeg
     * @return URL which was encoded as the given QR code
     * */
    suspend fun decodeQrCode(bytes: ByteArray, mimetype: String): QrCodeUrl =
        post("/api/v1/qrcode/decode"){
            setBody(formData {
                this.append("qrcode", bytes, Headers.build {
                    append(HttpHeaders.ContentType, mimetype)
                })
            })
        }.body()


    // user preference

    /**
     * List all preferences of the authenticated user.
     * @return List of user preferences
     * */
    suspend fun getAllUserPreferences(): List<UserPreference> =
        get("/api/v1/user/preferences").body<JsonArray>().let(UserPreference::parseMulti)

    /**
     * Returns a single preference of the authenticated user
     * @param key Name of the preference to retrieve
     * @return Returns the preference with the specified name. Throws an Exception if not found
     * */
    suspend fun getUserPreference(key: String): UserPreference =
        get("/api/v1/user/preferences/$key").body<JsonObject>().let(::UserPreference)

    /**
     * Updates a preference of the authenticated user.
     * @param key Name of the preference to update
     * @param req Update user preference request object
     * @return Updated user preference
     * */
    suspend fun updateUserPreference(key: String, req: UpdateUserPreferenceRequest): UserPreference =
        put("/api/v1/user/preferences/$key"){
            setBody(req)
        }.body<JsonObject>().let(::UserPreference)


    // settings (admin only)

    /**
     * List all application settings. This includes 2FAuth native settings and possible additional
     * admin-defined settings.
     * Settings endpoints are restricted to user with an Administrator role.
     * @return List of settings
     * */
    suspend fun getAllSettings(): List<Setting> =
        get("/api/v1/settings").body<JsonArray>().let(Setting::parseMulti)

     /**
      * Creates a new custom application setting. You are free to use this endpoint to store any
      * data you need to administrate your own app.
      * Settings endpoints are restricted to user with an Administrator role.
      * @param req Create setting request object
      * @return Newly created Setting object
      * */
    suspend fun createSetting(req: CreateSettingRequest): Setting =
        post("/api/v1/settings"){
            setBody(req)
        }.body<JsonObject>().let(::Setting)

    /**
     * Returns a single application setting, whether it is a native 2FAuth setting or a custom
     * setting.
     * Settings endpoints are restricted to user with an Administrator role.
     * @param key Name of the setting to retrieve
     * @return Setting corresponding to the given name
     * */
    suspend fun getSetting(key: String): Setting =
        get("/api/v1/settings/$key").body<JsonObject>().let(::Setting)

    /**
     * Updates an application setting, whether it is a native 2FAuth setting or a custom setting.
     * Will create the setting if it does not exist.
     * Settings endpoints are restricted to user with an Administrator role.
     * @param key Name of the setting to update
     * @param req Update setting request object
     * @return Updated setting object
     * */
    suspend fun updateSetting(key: String, req: UpdateSettingRequest): Setting =
        put("/api/v1/settings/$key"){
            setBody(req)
        }.body<JsonObject>().let(::Setting)

    /**
     * Deletes a custom application setting.
     * Settings endpoints are restricted to user with an Administrator role.
     * @param key Name of the setting to delete
     * @return True if deleted successfully
     * */
    suspend fun deleteSetting(key: String): Boolean =
        delete("/api/v1/settings/$key")

}