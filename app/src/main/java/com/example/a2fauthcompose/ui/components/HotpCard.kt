package com.example.a2fauthcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2fauthcompose.data.classes.HotpAccount
import com.example.a2fauthcompose.data.classes.HotpToken
import com.example.a2fauthcompose.data.classes.TotpToken

@OptIn(ExperimentalMaterial3Api::class)
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

    ElevatedCard(
        modifier = Modifier.padding(4.dp),
        onClick = onTap
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(text = name)
            Text(text = code)
        }
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