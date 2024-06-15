package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mcarr.a2fauthcompose.ui.screens.PreviewScreen
import dev.mcarr.a2fauthcompose.ui.topbar.LoadingScreenFab
import dev.mcarr.a2fauthcompose.ui.topbar.LoadingScreenTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.LoadingScreenTopBarRight

@Composable
fun LoadingScreenComponent() {

    CenteredFullscreenColumn {
        CircularProgressIndicator()
        Text(text = "Loading...")
    }

}

@Preview
@Composable
fun PreviewLoadingScreenComponent(){
    PreviewScreen(
        backButton = { LoadingScreenTopBarLeft() },
        doneButton = { LoadingScreenTopBarRight() },
        fab = { LoadingScreenFab() }
    ) {
        LoadingScreenComponent()
    }
}