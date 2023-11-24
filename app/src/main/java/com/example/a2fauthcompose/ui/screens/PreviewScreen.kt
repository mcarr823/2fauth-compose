package com.example.a2fauthcompose.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a2fauthcompose.ui.theme._2FAuthComposeTheme

@Composable
fun PreviewScreen(
    content: @Composable () -> Unit
) {

    _2FAuthComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) {
                Surface(
                    modifier = Modifier.padding(it)
                ) {
                    content()
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewPreviewScreen(){
    PreviewScreen {
        Text(text = "Text")
    }
}