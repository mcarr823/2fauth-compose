package com.example.a2fauthcompose.util

import com.example.a2fauthcompose.data.classes.TotpAccount

object MockPreviewUtil {

    val totpAccount = TotpAccount(
        id = 0,
        groupId = null,
        service = null,
        account = "MyAccount",
        icon = null,
        digits = 6,
        algorithm = "sha1",
        secret = null,
        period = 30
    )
    val httpUtil = HttpUtil(
        apiUrl = "",
        token = ""
    )
    val tokenUtil = TokenUtil(
        httpUtil = httpUtil,
        useHttp = false
    )

}