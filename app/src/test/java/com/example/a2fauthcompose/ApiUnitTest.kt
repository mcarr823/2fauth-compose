package com.example.a2fauthcompose

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


}