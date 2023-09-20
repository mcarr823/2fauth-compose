package com.example.a2fauthcompose

import com.example.a2fauthcompose.data.classes.Account
import com.example.a2fauthcompose.data.classes.CreateAccountRequest
import com.example.a2fauthcompose.data.classes.CreateOtpRequest
import com.example.a2fauthcompose.data.classes.UpdateAccountRequest
import com.example.a2fauthcompose.data.exceptions.Auth2FException404
import com.example.a2fauthcompose.util.Api
import com.example.a2fauthcompose.util.HttpUtil
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ApiUnitTest {

    private val api = Api(
        apiUrl = "https://2fa.my.example.domain",
        token = "xxxxxxxxxx",
        disableHttpsVerification = true,
        testing = true
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

}