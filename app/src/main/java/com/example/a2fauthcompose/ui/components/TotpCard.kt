package com.example.a2fauthcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.a2fauthcompose.data.classes.TotpToken
import kotlinx.coroutines.delay

@Composable
fun TotpCard(
    name: String,
    otp: TotpToken,
    expired: () -> Unit
) {

    val clipboard = LocalClipboardManager.current
    val code: String = remember {
        otp.password
    }

    val onTap: () -> Unit = {
        val text = AnnotatedString(code, ParagraphStyle())
        clipboard.setText(text)
        //TODO: show toast
    }

    PaddedCard(onTap = onTap) {
        Text(text = name)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = code)
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        do{
            delay(1_000)
            val now = System.currentTimeMillis()
        }while (now < otp.expiresAt)
        expired()
    })

}

@Preview
@Composable
fun PreviewTotpCard(){
    val name = "MyService"
    val otp = TotpToken("123456", -1L)
    PreviewComponent{
        TotpCard(
            name = name,
            otp = otp,
            expired = {}
        )
    }
}