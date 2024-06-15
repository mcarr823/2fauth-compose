package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptyOtpCard(
    name: String,
    onTap: () -> Unit
) {

    PaddedCard(onTap = onTap) {
        Text(text = name)
        Text(text = "Tap to generate")
    }

}

@Preview
@Composable
fun PreviewEmptyOtpCard(){
    PreviewComponent {
        EmptyOtpCard(
            name = "MyService",
            onTap = {}
        )
    }
}