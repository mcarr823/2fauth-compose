package dev.mcarr.a2fauthcompose.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.mcarr.a2fauthcompose.util.Api

class ApiViewModel : ViewModel() {

    var api by mutableStateOf(
        Api(
            apiUrl = "",
            token = ""
        )
    )

}