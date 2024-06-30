package dev.mcarr.a2fauthcompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.datastore.preferences.preferencesDataStore
import dev.mcarr.a2fauthcompose.ui.screens.MainActivityScreen
import dev.mcarr.a2fauthcompose.ui.theme._2FAuthComposeTheme
import dev.mcarr.a2fauthcompose.util.Api
import dev.mcarr.a2fauthcompose.viewmodels.ApiViewModel
import dev.mcarr.a2fauthcompose.viewmodels.OtpListScreenViewModel
import dev.mcarr.a2fauthcompose.viewmodels.SetupScreenViewModel


val Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _2FAuthComposeTheme {
                // A surface container using the 'background' color from the theme

                val setupScreenViewModel: SetupScreenViewModel by viewModels()
                val otpListScreenViewModel : OtpListScreenViewModel by viewModels()
                val apiViewModel: ApiViewModel by viewModels()

                apiViewModel.api = Api(setupScreenViewModel)

                MainActivityScreen(
                    setupScreenViewModel,
                    otpListScreenViewModel,
                    apiViewModel,
                    dataStore
                )

                
            }
        }
    }
    companion object{
        const val PAGE_SETUP = "PAGE_SETUP"
        const val PAGE_TEST_CONNECTION = "PAGE_TEST_CONNECTION"
        const val PAGE_LOADING = "PAGE_LOADING"
        const val PAGE_OTP_LIST = "PAGE_OTP_LIST"
    }
}