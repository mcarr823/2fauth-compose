package dev.mcarr.a2fauthcompose.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import dev.mcarr.a2fauthcompose.data.classes.TotpToken
import kotlinx.coroutines.delay

@Composable
fun TotpCard(
    name: String,
    otp: TotpToken,
    expired: () -> Unit
) {

    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    val code: String = remember {
        otp.password
    }
    val onTap: () -> Unit = {
        val text = AnnotatedString(code, ParagraphStyle())
        clipboard.setText(text)
        Toast.makeText(context, "Token copied to clipboard", Toast.LENGTH_SHORT).show()
        //TODO: show toast
    }

    var expiresInMillis by remember {
        mutableStateOf(otp.millisUntilExpired())
    }
    var expiresInText by remember {
        mutableStateOf("")
    }

    PaddedCard(onTap = onTap) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = name)
                Text(text = code)
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(progress = {
                    val periodMillis = otp.period * 1_000
                    (expiresInMillis.toDouble() / periodMillis.toDouble()).toFloat()
                })
                Text(text = expiresInText)
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {

        // Number of milliseconds to wait between each update
        val step = 100L

        // Start by calculating the offset between the expiry time
        // and the step, then wait for that amount of time.
        // This is to we can (roughly) have the token animations
        // and timing line up across different cards.
        if (expiresInMillis > 0) {
            val offset = expiresInMillis.rem(step)
            if (offset > 0){
                delay(offset)
                expiresInMillis -= offset
            }
        }

        // Loop until the token expires.
        // expiresInMillis and expiresInText are updated every
        // loop iteration so that the progress display on the
        // card is up to date.
        while (expiresInMillis > 0){
            delay(step)
            expiresInMillis -= step
            val expiresInSeconds = expiresInMillis / 1000
            expiresInText = expiresInSeconds.toString()
        }

        // Finally, run the expiry callback to tell the parent
        // that the token has expired and needs to be renewed.
        expired()

    })

}

@Preview
@Composable
fun PreviewTotpCard(){
    val name = "MyService"
    val otp = TotpToken("123456", -1L, 30)
    PreviewComponent{
        TotpCard(
            name = name,
            otp = otp,
            expired = {}
        )
    }
}