package com.example.a2fauthcompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2fauthcompose.ui.components.ToggleRow
import com.example.a2fauthcompose.ui.theme._2FAuthComposeTheme
import com.example.a2fauthcompose.viewmodels.SetupScreenViewModel

@Composable
fun SetupScreen(
    model: SetupScreenViewModel
) {

    var endpoint by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(model.endpoint))
    }
    var tokenId by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(model.token))
    }
    val (httpsCheckedState, httpsOnStateChange) = remember {
        mutableStateOf(model.disableHttpsVerification)
    }
    val (debugCheckedState, debugOnStateChange) = remember {
        mutableStateOf(model.debugMode)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = endpoint,
            onValueChange = { endpoint = it },
            label = { Text("Domain") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = "Base URL or domain.\n" +
                    "eg. https://2fauth.mydomain.com\n" +
                    "or 2fauth.mydomain.com",
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = tokenId,
            onValueChange = { tokenId = it },
            label = { Text("Access Token") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = "See the 'Settings > OAuth' section of your 2FAuth instance to generate personal access tokens",
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )

        ToggleRow(
            title = "Disable HTTPS verification",
            checkedState = httpsCheckedState,
            onStateChange = httpsOnStateChange
        )
        ToggleRow(
            title = "Enable debug mode",
            checkedState = debugCheckedState,
            onStateChange = debugOnStateChange
        )

    }

}

@Preview
@Composable
fun PreviewSetupScreen(){
    PreviewScreen {
        SetupScreen(
            model = SetupScreenViewModel()
        )
    }
}
