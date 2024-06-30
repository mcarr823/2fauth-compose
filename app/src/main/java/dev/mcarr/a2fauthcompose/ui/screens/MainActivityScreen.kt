package dev.mcarr.a2fauthcompose.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.rememberNavController
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_LOADING
import dev.mcarr.a2fauthcompose.ui.components.MainScreenNavHost
import dev.mcarr.a2fauthcompose.ui.components.MainScreenTopBar
import dev.mcarr.a2fauthcompose.viewmodels.ApiViewModel
import dev.mcarr.a2fauthcompose.viewmodels.OtpListScreenViewModel
import dev.mcarr.a2fauthcompose.viewmodels.SetupScreenViewModel

@Composable
fun MainActivityScreen(
    setupScreenViewModel: SetupScreenViewModel,
    otpListScreenViewModel: OtpListScreenViewModel,
    apiViewModel: ApiViewModel,
    dataStore: DataStore<Preferences>
) {

    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val goTo: (destination: String, popFirst: Boolean) -> Unit = { it, pop ->
            println("Going to $it")
            navController.navigate(it){
                if (pop) this.popUpTo(PAGE_LOADING)
            }
        }

        var currentPage by remember { mutableStateOf("") }
        val setCurrentPage: (String) -> Unit = { currentPage = it }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MainScreenTopBar(currentPage, setupScreenViewModel, goTo)
            },
            floatingActionButton = {
                                    //TODO
            },
            floatingActionButtonPosition = FabPosition.End
        ) { padding ->

            MainScreenNavHost(
                navController,
                padding,
                setCurrentPage,
                setupScreenViewModel,
                dataStore,
                apiViewModel,
                goTo,
                otpListScreenViewModel
            )

        }
    }

}