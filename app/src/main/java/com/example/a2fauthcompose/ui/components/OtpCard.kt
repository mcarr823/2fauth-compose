package com.example.a2fauthcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.a2fauthcompose.data.classes.AbstractAccount
import com.example.a2fauthcompose.data.classes.AbstractToken
import com.example.a2fauthcompose.data.classes.HotpToken
import com.example.a2fauthcompose.data.classes.TotpToken
import com.example.a2fauthcompose.util.MockPreviewUtil
import com.example.a2fauthcompose.util.TokenUtil

@Composable
fun OtpCard(
    account: AbstractAccount,
    util: TokenUtil
) {

    val name = remember {
        if (account.service != null)
            "${account.service} (${account.account})"
        else
            account.account
    }
    var otp: AbstractToken? = remember {
        null
    }
    var generating: Boolean = remember {
        false
    }

    if (otp is HotpToken){
        HotpCard(name = name, otp = otp)
    }else if (otp is TotpToken){
        TotpCard(
            name = name,
            otp = otp,
            expired = {
                otp = null
            }
        )
    }else if (generating){
        LoadingOtpCard(
            name = name,
            account = account,
            util = util,
            onSuccess = {
                otp = it
            },
            onError = {
                //TODO: show error
            }
        )
    }else{
        EmptyOtpCard(
            name = name,
            onTap = {
                generating = true
            }
        )
    }

}

@Preview
@Composable
fun PreviewOtpCard(){
    val account = MockPreviewUtil.totpAccount
    val util = MockPreviewUtil.tokenUtil
    PreviewComponent {
        OtpCard(
            account = account,
            util = util
        )
    }
}
