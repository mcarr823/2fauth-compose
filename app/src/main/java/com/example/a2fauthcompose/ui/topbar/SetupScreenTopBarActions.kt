package com.example.a2fauthcompose.ui.topbar

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SetupScreenTopBarLeft(onClick: () -> Unit) {

    TextButton(onClick = onClick) {
        Text("Cancel")
    }

}

@Composable
fun SetupScreenTopBarRight(onClick: () -> Unit){

    TextButton(onClick = onClick) {
        Text("Save")
    }

}

@Composable
fun SetupScreenFab(){
    //
}