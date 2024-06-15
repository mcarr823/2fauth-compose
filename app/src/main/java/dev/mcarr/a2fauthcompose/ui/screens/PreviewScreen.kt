package dev.mcarr.a2fauthcompose.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.mcarr.a2fauthcompose.ui.theme._2FAuthComposeTheme
import dev.mcarr.a2fauthcompose.ui.topbar.SetupScreenFab
import dev.mcarr.a2fauthcompose.ui.topbar.SetupScreenTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.SetupScreenTopBarRight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    backButton: @Composable () -> Unit,
    doneButton: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    content: @Composable () -> Unit
) {

    _2FAuthComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "2FAuth") },
                        navigationIcon = { backButton() },
                        actions = { doneButton() }
                    )
                },
                floatingActionButton = { fab() },
                floatingActionButtonPosition = FabPosition.End
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
    PreviewScreen(
        backButton = { SetupScreenTopBarLeft {} },
        doneButton = { SetupScreenTopBarRight {} },
        fab = { SetupScreenFab() }
    ) {
        Text(text = "Text")
    }
}