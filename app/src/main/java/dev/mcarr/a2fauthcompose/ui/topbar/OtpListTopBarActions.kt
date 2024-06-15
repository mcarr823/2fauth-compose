package dev.mcarr.a2fauthcompose.ui.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun OtpListTopBarLeft(onClick: () -> Unit) {

    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings"
        )
    }

}

@Composable
fun OtpListTopBarRight(){

    //None

}

@Composable
fun OtpListFab(){
    //
}