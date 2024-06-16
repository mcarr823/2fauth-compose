package dev.mcarr.a2fauthcompose.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import dev.mcarr.a2fauthcompose.data.classes.AbstractToken
import dev.mcarr.a2fauthcompose.data.classes.HotpToken
import dev.mcarr.a2fauthcompose.data.classes.TotpToken
import dev.mcarr.a2fauthcompose.util.MockPreviewUtil
import dev.mcarr.a2fauthcompose.util.TokenUtil
import dev.mcarr.a2fauthcompose.viewmodels.AbstractTokenViewModel

@Composable
fun <T : AbstractToken>OtpCard(
    model: AbstractTokenViewModel<T>,
    util: TokenUtil
) {

    val account = remember {
        model.account
    }
    val name = remember {
        if (account.service != null)
            "${account.service} (${account.account})"
        else
            account.account
    }
    var otp: T? by remember {
        model.otp
    }
    var generating: Boolean by remember {
        mutableStateOf(otp == null)
    }

    if (otp == null || generating){
        LoadingOtpCard(
            name = name,
            account = account,
            util = util
        )
    }else if (otp is HotpToken){
        HotpCard(name = name, otp = otp as HotpToken)
    }else if (otp is TotpToken){
        TotpCard(
            name = name,
            otp = otp as TotpToken,
            expired = {
                Log.i("OtpCard", "Totp expired")
                generating = true
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

    LaunchedEffect(key1 = generating, block = {
        if (generating) {
            Log.i("LoadingOtpCard", "Launched effect")
            try {
                model.otp.value = util.getOtp<T>(account) as T
                Log.i("LoadingOtpCard", "Success")
            } catch (e: Exception) {
                Log.i("LoadingOtpCard", "Exception")
                e.printStackTrace()
                //TODO: show error
            }
            Log.i("LoadingOtpCard", "Finished")
            generating = false
        }
    })

}

@Preview
@Composable
fun PreviewOtpCard(){
    val account = MockPreviewUtil.totpAccount
    val util = MockPreviewUtil.tokenUtil
    val model = AbstractTokenViewModel<TotpToken>(account)
    PreviewComponent {
        OtpCard(
            model = model,
            util = util
        )
    }
}
