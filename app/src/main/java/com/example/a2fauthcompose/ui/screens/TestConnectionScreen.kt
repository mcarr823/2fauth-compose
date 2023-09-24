package com.example.a2fauthcompose.ui.screens

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2fauthcompose.data.exceptions.Auth2FException401
import com.example.a2fauthcompose.data.exceptions.Auth2FException403
import com.example.a2fauthcompose.ui.theme._2FAuthComposeTheme
import com.example.a2fauthcompose.util.Api
import com.example.a2fauthcompose.viewmodels.SetupScreenViewModel

@Composable
fun TestConnectionScreen(
    model: SetupScreenViewModel,
    enableBackButton: (Boolean) -> Unit,
    success: (Api) -> Unit
) {

    val primaryColor = MaterialTheme.colorScheme.onBackground
    var message by remember { mutableStateOf("Checking connection...") }
    var messageColor by remember { mutableStateOf(primaryColor) }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color.Blue
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = message,
                color = messageColor
            )
        }
    }

    LaunchedEffect(Unit){
        val api = Api(
            apiUrl = model.endpoint,
            token = model.token,
            disableHttpsVerification = model.disableHttpsVerification,
            testing = model.debugMode
        )
        try {
            api.getAll2FaAccounts(withSecret = false)
            success(api)
        } catch (e: Auth2FException401){
            e.printStackTrace()
            messageColor = Color.Red
            message = "Connection succeeded, but authentication failed.\n" +
                    "Please check authentication token"
            enableBackButton(true)
        } catch (e: Auth2FException403){
            e.printStackTrace()
            messageColor = Color.Red
            message = "Access forbidden. Check URL on setup page"
            enableBackButton(true)
        } catch (e: Exception) {
            e.printStackTrace()
            messageColor = Color.Red
            message = e.localizedMessage ?: "Connection attempt failed"
            enableBackButton(true)
        }
    }

}

@Preview
@Composable
fun PreviewTestConnectionScreen(){
    _2FAuthComposeTheme {
        Surface {
            TestConnectionScreen(
                model = SetupScreenViewModel(),
                enableBackButton = {},
                success = {}
            )
        }
    }
}