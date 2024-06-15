package com.example.a2fauthcompose

import com.example.a2fauthcompose.data.classes.TotpAccount
import com.example.a2fauthcompose.data.classes.TotpToken
import com.example.a2fauthcompose.util.Api
import com.example.a2fauthcompose.util.TokenUtil
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TokenUtilUnitTest {

    private val api = Api(
        apiUrl = BuildConfig.API_DOMAIN,
        token = BuildConfig.API_TOKEN,
        httpsVerification = BuildConfig.API_HTTPS_VERIFICATION,
        debugMode = BuildConfig.API_DEBUG_MODE
    )

    @Test
    fun testOffline(){
        val tokenUtil = TokenUtil(
            httpUtil = api.httpUtil
        )

        runTest {

            assertNotEquals("", api.httpUtil.apiUrl, "API endpoint not specified")
            assertNotEquals("", api.httpUtil.token, "API token not specified")

            val account =
                try{
                    api.get2FaAccount(2, true)
                }catch (e: Exception){
                    //e.printStackTrace()
                    null
                }
            assertNotNull(account, "Failed to retrieve account with ID of 0")

            val otp =
                try{
                    api.getOtp(account.id)
                }catch (e: Exception){
                    //e.printStackTrace()
                    null
                }
            assertNotNull(otp, "Failed to retrieve OTP")

            val totpAccount = TotpAccount(account)
            val localToken = tokenUtil.getOtp(totpAccount)
            assertTrue(localToken is TotpToken, "Token is not a TotpToken")

            assertEquals(
                localToken.password,
                otp.password,
                "Tokens do not match. ${localToken.password} != ${otp.password}"
            )
        }
    }

}