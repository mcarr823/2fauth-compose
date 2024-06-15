package dev.mcarr.a2fauthcompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.mcarr.a2fauthcompose.ui.screens.LoadingScreen
import dev.mcarr.a2fauthcompose.ui.screens.OtpListScreen
import dev.mcarr.a2fauthcompose.ui.screens.SetupScreen
import dev.mcarr.a2fauthcompose.ui.screens.TestConnectionScreen
import dev.mcarr.a2fauthcompose.ui.theme._2FAuthComposeTheme
import dev.mcarr.a2fauthcompose.ui.topbar.LoadingScreenTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.LoadingScreenTopBarRight
import dev.mcarr.a2fauthcompose.ui.topbar.OtpListTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.OtpListTopBarRight
import dev.mcarr.a2fauthcompose.ui.topbar.SetupScreenTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.SetupScreenTopBarRight
import dev.mcarr.a2fauthcompose.ui.topbar.TestConnectionTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.TestConnectionTopBarRight
import dev.mcarr.a2fauthcompose.util.Api
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

                var api = Api(setupScreenViewModel)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val goTo: (destination: String, popFirst: Boolean) -> Unit = { it, pop ->
                        println("Going to $it")
                        navController.navigate(it){
                            if (pop) this.popUpTo(PAGE_LOADING)
                        }
                    }

                    var currentPage by remember {
                        mutableStateOf("")
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(text = "2FAuth") },
                                navigationIcon = {
                                    when(currentPage){
                                        PAGE_LOADING -> LoadingScreenTopBarLeft()
                                        PAGE_SETUP -> SetupScreenTopBarLeft{
                                            //Empty the viewmodel and go back to the loading screen
                                            //so it can reload from datastore and revert the viewmodel
                                            //to its previous state
                                            setupScreenViewModel.reset()
                                            goTo(PAGE_LOADING, true)
                                        }
                                        PAGE_TEST_CONNECTION -> TestConnectionTopBarLeft()
                                        PAGE_OTP_LIST -> OtpListTopBarLeft {
                                            goTo(PAGE_SETUP, true)
                                        }
                                    }
                                },
                                actions = {

                                    when(currentPage){
                                        PAGE_LOADING -> LoadingScreenTopBarRight()
                                        PAGE_SETUP -> SetupScreenTopBarRight{ goTo(PAGE_TEST_CONNECTION, false) }
                                        PAGE_TEST_CONNECTION -> TestConnectionTopBarRight()
                                        PAGE_OTP_LIST -> OtpListTopBarRight()
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                                               //TODO
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) { padding ->

                        NavHost(
                            navController = navController,
                            modifier = Modifier.padding(padding),
                            startDestination = PAGE_LOADING
                        ) {

                            composable(PAGE_LOADING){
                                currentPage = PAGE_LOADING
                                LoadingScreen(
                                    model = setupScreenViewModel,
                                    dataStore = dataStore,
                                    goToOtpPage = {
                                        api = Api(setupScreenViewModel)
                                        goTo(PAGE_OTP_LIST, false)
                                    },
                                    goToSetupPage = { goTo(PAGE_SETUP, false) }
                                )
                            }

                            composable(PAGE_SETUP){
                                currentPage = PAGE_SETUP
                                SetupScreen(
                                    model = setupScreenViewModel
                                )
                            }

                            composable(PAGE_TEST_CONNECTION){
                                currentPage = PAGE_TEST_CONNECTION
                                TestConnectionScreen(
                                    model = setupScreenViewModel,
                                    dataStore = dataStore,
                                    cancel = {
                                        navController.popBackStack()
                                    },
                                    success = {
                                        api = Api(setupScreenViewModel)
                                        goTo(PAGE_OTP_LIST, true)
                                    }
                                )
                            }

                            composable(PAGE_OTP_LIST){
                                currentPage = PAGE_OTP_LIST
                                OtpListScreen(
                                    model = otpListScreenViewModel,
                                    api = api
                                )
                            }
                        }

                    }
                }
            }
        }
    }
    companion object{
        private const val PAGE_SETUP = "PAGE_SETUP"
        private const val PAGE_TEST_CONNECTION = "PAGE_TEST_CONNECTION"
        private const val PAGE_LOADING = "PAGE_LOADING"
        private const val PAGE_OTP_LIST = "PAGE_OTP_LIST"
    }
}