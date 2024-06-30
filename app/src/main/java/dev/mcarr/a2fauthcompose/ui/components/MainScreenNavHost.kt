package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_LOADING
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_OTP_LIST
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_SETUP
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_TEST_CONNECTION
import dev.mcarr.a2fauthcompose.ui.screens.LoadingScreen
import dev.mcarr.a2fauthcompose.ui.screens.OtpListScreen
import dev.mcarr.a2fauthcompose.ui.screens.SetupScreen
import dev.mcarr.a2fauthcompose.ui.screens.TestConnectionScreen
import dev.mcarr.a2fauthcompose.util.Api
import dev.mcarr.a2fauthcompose.viewmodels.ApiViewModel
import dev.mcarr.a2fauthcompose.viewmodels.OtpListScreenViewModel
import dev.mcarr.a2fauthcompose.viewmodels.SetupScreenViewModel

@Composable
fun MainScreenNavHost(
    navController: NavHostController,
    padding: PaddingValues,
    setCurrentPage: (value: String) -> Unit,
    setupScreenViewModel: SetupScreenViewModel,
    dataStore: DataStore<Preferences>,
    apiViewModel: ApiViewModel,
    goTo: (destination: String, popFirst: Boolean) -> Unit,
    otpListScreenViewModel: OtpListScreenViewModel
) {

    NavHost(
        navController = navController,
        modifier = Modifier.padding(padding),
        startDestination = PAGE_LOADING
    ) {

        loading(
            setCurrentPage,
            setupScreenViewModel,
            dataStore,
            apiViewModel,
            goTo
        )

        setup(
            setCurrentPage,
            setupScreenViewModel
        )

        testConnectionComposable(
            setCurrentPage,
            setupScreenViewModel,
            dataStore,
            navController,
            apiViewModel,
            goTo
        )

        otpListComposable(
            otpListScreenViewModel,
            apiViewModel.api,
            setCurrentPage
        )
    }

}

private fun NavGraphBuilder.loading(
    setCurrentPage: (value: String) -> Unit,
    setupScreenViewModel: SetupScreenViewModel,
    dataStore: DataStore<Preferences>,
    apiViewModel: ApiViewModel,
    goTo: (destination: String, popFirst: Boolean) -> Unit
) {

    composable(PAGE_LOADING){
        setCurrentPage(PAGE_LOADING)
        LoadingScreen(
            model = setupScreenViewModel,
            dataStore = dataStore,
            goToOtpPage = {
                apiViewModel.api = Api(setupScreenViewModel)
                goTo(PAGE_OTP_LIST, false)
            },
            goToSetupPage = { goTo(PAGE_SETUP, false) }
        )
    }

}

private fun NavGraphBuilder.setup(
    setCurrentPage: (value: String) -> Unit,
    setupScreenViewModel: SetupScreenViewModel
) {

    composable(PAGE_SETUP){
        setCurrentPage(PAGE_SETUP)
        SetupScreen(
            model = setupScreenViewModel
        )
    }

}

private fun NavGraphBuilder.testConnectionComposable(
    setCurrentPage: (value: String) -> Unit,
    setupScreenViewModel: SetupScreenViewModel,
    dataStore: DataStore<Preferences>,
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    goTo: (destination: String, popFirst: Boolean) -> Unit
) {

    composable(PAGE_TEST_CONNECTION){
        setCurrentPage(PAGE_TEST_CONNECTION)
        TestConnectionScreen(
            model = setupScreenViewModel,
            dataStore = dataStore,
            cancel = {
                navController.popBackStack()
            },
            success = {
                apiViewModel.api = Api(setupScreenViewModel)
                goTo(PAGE_OTP_LIST, true)
            }
        )
    }

}

private fun NavGraphBuilder.otpListComposable(
    otpListScreenViewModel: OtpListScreenViewModel,
    api: Api,
    setCurrentPage: (value: String) -> Unit
) {

    composable(PAGE_OTP_LIST){
        setCurrentPage(PAGE_OTP_LIST)
        OtpListScreen(
            model = otpListScreenViewModel,
            api = api
        )
    }

}