package com.example.a2fauthcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a2fauthcompose.ui.theme._2FAuthComposeTheme

@Composable
fun PreviewComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    content: @Composable () -> Unit
) {

    _2FAuthComposeTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Surface(
                modifier = modifier,
            ) {
                content()
            }
        }
    }

}

@Preview
@Composable
fun PreviewPreviewComponent(){
    PreviewComponent {
        Text(text = "Test component")
    }
}