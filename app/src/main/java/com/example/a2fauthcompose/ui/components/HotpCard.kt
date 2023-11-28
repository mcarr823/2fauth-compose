package com.example.a2fauthcompose.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.a2fauthcompose.data.classes.HotpToken

@Composable
fun HotpCard(
    name: String,
    otp: HotpToken
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
        Text(text = code)
    }

}

@Preview
@Composable
fun PreviewHotpCard(){
    val name = "MyService"
    val otp = HotpToken("123456")
    PreviewComponent {
        HotpCard(
            name = name,
            otp = otp
        )
    }
}