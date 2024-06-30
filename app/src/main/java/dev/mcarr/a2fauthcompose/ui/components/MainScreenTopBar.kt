package dev.mcarr.a2fauthcompose.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_LOADING
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_OTP_LIST
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_SETUP
import dev.mcarr.a2fauthcompose.MainActivity.Companion.PAGE_TEST_CONNECTION
import dev.mcarr.a2fauthcompose.ui.topbar.LoadingScreenTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.LoadingScreenTopBarRight
import dev.mcarr.a2fauthcompose.ui.topbar.OtpListTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.OtpListTopBarRight
import dev.mcarr.a2fauthcompose.ui.topbar.SetupScreenTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.SetupScreenTopBarRight
import dev.mcarr.a2fauthcompose.ui.topbar.TestConnectionTopBarLeft
import dev.mcarr.a2fauthcompose.ui.topbar.TestConnectionTopBarRight
import dev.mcarr.a2fauthcompose.viewmodels.SetupScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar(
    currentPage: String,
    setupScreenViewModel: SetupScreenViewModel,
    goTo: (destination: String, popFirst: Boolean) -> Unit
) {

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

}