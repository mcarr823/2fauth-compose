package com.example.a2fauthcompose

import com.example.a2fauthcompose.data.classes.Account
import com.example.a2fauthcompose.data.classes.CreateAccountRequest
import com.example.a2fauthcompose.data.classes.CreateGroupRequest
import com.example.a2fauthcompose.data.classes.CreateOtpRequest
import com.example.a2fauthcompose.data.classes.UpdateAccountRequest
import com.example.a2fauthcompose.data.classes.UpdateGroupRequest
import com.example.a2fauthcompose.data.exceptions.Auth2FException404
import com.example.a2fauthcompose.data.exceptions.Auth2FException422
import com.example.a2fauthcompose.util.Api
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class ApiUnitTest {

    private val api = Api(
        apiUrl = BuildConfig.API_DOMAIN,
        token = BuildConfig.API_TOKEN,
        httpsVerification = BuildConfig.API_HTTPS_VERIFICATION,
        debugMode = BuildConfig.API_DEBUG_MODE
    )


    @Test
    fun testAccountsApiRequests(){

        val service = "MyTestSite"
        val accountName = "john.doe"
        val accountName2 = "john.doe2"
        val icon: String? = null
        val otpType = "totp"
        val digits = 6
        val algorithm = "sha1"
        val secret = "GJTGC5LUNA======"
        val period = 30
        val counter: Int? = null
        val newAccountRequest = CreateAccountRequest(
            service = service,
            account = accountName,
            icon = icon,
            otp_type = otpType,
            digits = digits,
            algorithm = algorithm,
            secret = secret,
            period = period,
            counter = counter
        )
        val updateAccountRequest = UpdateAccountRequest(
            service = service,
            account = accountName2,
            icon = icon,
            otp_type = otpType,
            digits = digits,
            algorithm = algorithm,
            secret = secret,
            period = period,
            counter = counter
        )

        runTest {
            val originalNumberOfAccounts =
                try {
                    val accounts = api.getAll2FaAccounts(withSecret = false)
                    accounts.size
                }catch (e: Exception){
                    e.printStackTrace()
                    -1
                }
            assertNotEquals(-1, originalNumberOfAccounts)

            val account =
                try {
                    api.create2FaAccount(req = newAccountRequest)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(account)
            assertEquals(service, account.service)
            assertEquals(accountName, account.account)
            assertEquals(icon, account.icon)
            assertEquals(otpType, account.otp_type)
            assertEquals(digits, account.digits)
            assertEquals(algorithm, account.algorithm)
            assertEquals(secret, account.secret)
            assertEquals(period, account.period)
            assertEquals(counter, account.counter)

            val numberOfAccountsAfterCreate =
                try {
                    val accounts = api.getAll2FaAccounts(withSecret = false)
                    accounts.size
                }catch (e: Exception){
                    e.printStackTrace()
                    -1
                }
            assertNotEquals(-1, numberOfAccountsAfterCreate)
            assertEquals(originalNumberOfAccounts + 1, numberOfAccountsAfterCreate)

            val updatedAccount =
                try {
                    api.update2FaAccount(id = account.id, req = updateAccountRequest)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(updatedAccount)
            assertEquals(service, updatedAccount.service)
            assertEquals(accountName2, updatedAccount.account)
            assertEquals(icon, updatedAccount.icon)
            assertEquals(otpType, updatedAccount.otp_type)
            assertEquals(digits, updatedAccount.digits)
            assertEquals(algorithm, updatedAccount.algorithm)
            assertEquals(secret, updatedAccount.secret)
            assertEquals(period, updatedAccount.period)
            assertEquals(counter, updatedAccount.counter)

            val success =
                try {
                    api.delete2FaAccount(id = account.id)
                }catch (e: Exception){
                    e.printStackTrace()
                    false
                }
            assert(success)

            val deleteWasSuccessful =
                try {
                    api.get2FaAccount(id = account.id, false)
                    false
                }catch (e: Auth2FException404){
                    //Expected exception
                    true
                }catch (e: Exception){
                    //Unexpected exception
                    e.printStackTrace()
                    false
                }
            assert(deleteWasSuccessful)

            val numberOfAccountsAfterDelete =
                try {
                    val accounts = api.getAll2FaAccounts(withSecret = false)
                    accounts.size
                }catch (e: Exception){
                    e.printStackTrace()
                    -1
                }
            assertNotEquals(-1, numberOfAccountsAfterDelete)
            assertEquals(originalNumberOfAccounts, numberOfAccountsAfterDelete)
            assertEquals(numberOfAccountsAfterCreate - 1, numberOfAccountsAfterDelete)

            val newAccountsToCreate = 5
            val newAccounts: List<Account> =
                try {
                    (0 until newAccountsToCreate).map {
                        api.create2FaAccount(req = newAccountRequest)
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    listOf()
                }
            assertEquals(newAccountsToCreate, newAccounts.size)

            val successfullyDeletedSeveralAccounts =
                try {
                    val ids = newAccounts.map { it.id }.toIntArray()
                    api.delete2FaAccount(ids = ids)
                }catch (e: Exception){
                    e.printStackTrace()
                    false
                }
            assert(successfullyDeletedSeveralAccounts)

            val deletesWereSuccessful =
                newAccounts.all {
                    try {
                        api.get2FaAccount(id = it.id, false)
                        false
                    }catch (e: Auth2FException404){
                        //Expected exception
                        true
                    }catch (e: Exception){
                        //Unexpected exception
                        e.printStackTrace()
                        false
                    }
                }
            assert(deletesWereSuccessful)

        }

    }

    @Test
    fun testOtpApiRequests(){

        val service = "MyTestSite"
        val accountName = "john.doe"
        val icon: String? = null
        val otpType = "totp"
        val digits = 6
        val digits2 = 5
        val algorithm = "sha1"
        val secret = "GJTGC5LUNA======"
        val period = 30
        val counter: Int? = null
        val newAccountRequest = CreateAccountRequest(
            service = service,
            account = accountName,
            icon = icon,
            otp_type = otpType,
            digits = digits,
            algorithm = algorithm,
            secret = secret,
            period = period,
            counter = counter
        )
        val newOtpRequest = CreateOtpRequest(
            service = service,
            account = accountName,
            icon = icon,
            otp_type = otpType,
            digits = digits,
            algorithm = algorithm,
            secret = secret,
            period = period,
            counter = counter
        )
        val newOtpRequest2 = CreateOtpRequest(
            service = service,
            account = accountName,
            icon = icon,
            otp_type = otpType,
            digits = digits2,
            algorithm = algorithm,
            secret = secret,
            period = period,
            counter = counter
        )

        runTest {
            val account =
                try {
                    api.create2FaAccount(req = newAccountRequest)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(account)

            val otp =
                try {
                    api.getOtp(account.id)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(otp)

            val otp2 =
                try {
                    api.getOtp(newOtpRequest)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(otp2)

            val otp3 =
                try {
                    api.getOtp(newOtpRequest2)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(otp3)

            assertEquals(otp.password, otp2.password)
            assertNotEquals(otp.password, otp3.password)

            val success =
                try {
                    api.delete2FaAccount(account.id)
                }catch (e: Exception){
                    e.printStackTrace()
                    false
                }
            assert(success)
        }

    }

    @Test
    fun testGroupApiRequests(){

        val groupName = "My new test group"
        val groupName2 = "My new test group2"
        val newGroupRequest = CreateGroupRequest(name = groupName)
        val updateGroupRequest = UpdateGroupRequest(name = groupName2)

        runTest {

            val originalNumberOfGroups =
                try {
                    val groups = api.getAllGroups()
                    groups.size
                }catch (e: Exception){
                    e.printStackTrace()
                    -1
                }
            assertNotEquals(-1, originalNumberOfGroups)

            val group =
                try {
                    api.createGroup(req = newGroupRequest)
                }catch (e: Auth2FException422){
                    //Group with this name already exists
                    e.printStackTrace()
                    null
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(group)

            val numberOfGroupsAfterCreate =
                try {
                    val groups = api.getAllGroups()
                    groups.size
                }catch (e: Exception){
                    e.printStackTrace()
                    -1
                }
            assertNotEquals(-1, numberOfGroupsAfterCreate)
            assertEquals(originalNumberOfGroups + 1, numberOfGroupsAfterCreate)

            val updatedGroup =
                try {
                    api.updateGroup(id = group.id, req = updateGroupRequest)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(updatedGroup)
            assertEquals(groupName2, updatedGroup.name)

            val success =
                try {
                    api.deleteGroup(id = group.id)
                }catch (e: Exception){
                    e.printStackTrace()
                    false
                }
            assert(success)

            val deleteWasSuccessful =
                try {
                    api.getGroup(id = group.id)
                    false
                }catch (e: Auth2FException404){
                    //Expected exception
                    true
                }catch (e: Exception){
                    //Unexpected exception
                    e.printStackTrace()
                    false
                }
            assert(deleteWasSuccessful)

            val numberOfGroupsAfterDelete =
                try {
                    val groups = api.getAllGroups()
                    groups.size
                }catch (e: Exception){
                    e.printStackTrace()
                    -1
                }
            assertNotEquals(-1, numberOfGroupsAfterDelete)
            assertEquals(originalNumberOfGroups, numberOfGroupsAfterDelete)
            assertEquals(numberOfGroupsAfterCreate - 1, numberOfGroupsAfterDelete)
        }

    }

    /**
     * This test requires at least 1 account to exist in the 2fauth instance before it can run.
     * */
    @Test
    fun testQrCodeApiRequests(){

        runTest {
            val account =
                try {
                    val accounts = api.getAll2FaAccounts(withSecret = false)
                    accounts.firstOrNull()
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(account)

            val qrCode =
                try {
                    api.getQrCode(account.id)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(qrCode)

            //This test currently won't run because Base64.decode isn't mocked
            /*val decodedBytes = Base64.decode(qrCode.qrcode, Base64.DEFAULT)
            val qrCodeUrl =
                try {
                    api.decodeQrCode(decodedBytes, "image/png")
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }

            assertNotNull(qrCodeUrl)*/

        }

    }
    @Test
    fun testUserPreferenceApiRequests(){

        val fakePreferenceKey = "ThisIsNotARealPreference"

        runTest {

            val preferences =
                try {
                    api.getAllUserPreferences()
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(preferences)
            assertNotEquals(0, preferences.size)

            val firstPref = preferences.firstOrNull()
            assertNotNull(firstPref)

            println("Getting pref: ${firstPref.key}")

            //FIXME Below test not currently working
            //Matches the spec, but returns a 404 error even though the key was returned
            // by the above function.
            //Might need to update 2fauth server. May have changed since spec was written
            val preference =
                try {
                    api.getUserPreference(firstPref.key)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(preference)
            assertEquals(firstPref.key, preference.key)
            assertEquals(firstPref.value, preference.value)

            val success =
                try {
                    api.getUserPreference(fakePreferenceKey)
                    false
                }catch (e: Auth2FException404){
                    //Expected exception
                    true
                }catch (e: Exception){
                    e.printStackTrace()
                    false
                }
            assert(success)
        }
    }
    @Test
    fun testSettingApiRequests(){

        val fakePreferenceKey = "ThisIsNotARealPreference"

        runTest {

            val settings =
                try {
                    api.getAllSettings()
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(settings)
            assertNotEquals(0, settings.size)

            val firstPref = settings.firstOrNull()
            assertNotNull(firstPref)

            println("Getting pref: ${firstPref.key}")

            //FIXME Below test not currently working
            //Matches the spec, but returns html instead of expected json.
            //Might need to update 2fauth server. Could be a new method
            val setting =
                try {
                    api.getSetting(firstPref.key)
                }catch (e: Exception){
                    e.printStackTrace()
                    null
                }
            assertNotNull(setting)
            assertEquals(firstPref.key, setting.key)
            assertEquals(firstPref.value, setting.value)

            val success =
                try {
                    api.getSetting(fakePreferenceKey)
                    false
                }catch (e: Auth2FException404){
                    //Expected exception
                    true
                }catch (e: Exception){
                    e.printStackTrace()
                    false
                }
            assert(success)
        }
    }

}