package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import dev.mcarr.a2fauthcompose.data.classes.AbstractAccount
import dev.mcarr.a2fauthcompose.data.classes.AbstractToken
import dev.mcarr.a2fauthcompose.data.classes.TotpAccount
import dev.mcarr.a2fauthcompose.util.HttpUtil
import dev.mcarr.a2fauthcompose.util.MockPreviewUtil
import dev.mcarr.a2fauthcompose.util.TokenUtil

@Composable
fun LoadingOtpCard(
    name: String,
    account: AbstractAccount,
    util: TokenUtil,
    onSuccess: (AbstractToken) -> Unit,
    onError: (Exception) -> Unit
) {

    PaddedCard {
        Text(text = name)
        Text(text = "Generating...")
    }

    LaunchedEffect(key1 = Unit, block = {
        try {
            util.getOtp(account).let(onSuccess)
        }catch (e: Exception){
            onError(e)
        }
    })

}

@Preview
@Composable
fun PreviewLoadingOtpCard(){
    val account = MockPreviewUtil.totpAccount
    val util = MockPreviewUtil.tokenUtil
    PreviewComponent {
        LoadingOtpCard(
            name = "ServiceName",
            account = account,
            util = util,
            onSuccess = {},
            onError = {}
        )
    }
}