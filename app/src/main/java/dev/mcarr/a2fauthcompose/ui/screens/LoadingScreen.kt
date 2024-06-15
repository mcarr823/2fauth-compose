package dev.mcarr.a2fauthcompose.ui.screens

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dev.mcarr.a2fauthcompose.ui.components.CenteredFullscreenColumn
import dev.mcarr.a2fauthcompose.ui.components.LoadingScreenComponent
import dev.mcarr.a2fauthcompose.viewmodels.SetupScreenViewModel
import kotlinx.coroutines.flow.map

@Composable
fun LoadingScreen(
    model: SetupScreenViewModel,
    dataStore: DataStore<Preferences>,
    goToSetupPage: () -> Unit,
    goToOtpPage: () -> Unit
) {

    LoadingScreenComponent()

    LaunchedEffect(key1 = Unit, block = {

        //If the model is empty, load the settings from disk
        if (model.isEmpty()){
            model.readFromDataStore(dataStore)
        }

        //If the model is STILL empty, then that means we don't
        //have any saved preferences, so go to the setup page
        if (model.isEmpty()){
            goToSetupPage()
        }else{
            goToOtpPage()
        }

    })

}