package dev.mcarr.a2fauthcompose.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import dev.mcarr.a2fauthcompose.data.classes.AbstractAccount
import dev.mcarr.a2fauthcompose.data.classes.AbstractToken
import dev.mcarr.a2fauthcompose.data.classes.HotpAccount
import dev.mcarr.a2fauthcompose.data.classes.HotpToken
import dev.mcarr.a2fauthcompose.data.classes.TotpAccount
import dev.mcarr.a2fauthcompose.data.classes.TotpToken
import dev.mcarr.a2fauthcompose.util.MockPreviewUtil
import dev.mcarr.a2fauthcompose.util.TokenUtil
import dev.mcarr.a2fauthcompose.viewmodels.TotpTokenViewModel

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
    var otp: AbstractToken? by remember {
        mutableStateOf(account.generate())
    }
    var generating: Boolean by remember {
        mutableStateOf(false)
    }

    if (otp is HotpToken){
        HotpCard(name = name, otp = otp as HotpToken)
    }else if (otp is TotpToken){
        TotpCard(
            name = name,
            otp = otp as TotpToken,
            expired = {
                Log.i("OtpCard", "Totp expired")
                otp = account.generate()
            }
        )
    }else if (generating){
        LoadingOtpCard(
            name = name,
            account = account,
            util = util,
            onSuccess = {
                Log.i("OtpCard", "Loading success")
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
                Log.i("OtpCard", "Tap")
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
