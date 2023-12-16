package com.example.a2fauthcompose.viewmodels

import androidx.lifecycle.ViewModel
import com.example.a2fauthcompose.util.Api

class SetupScreenViewModel : ViewModel() {

    var endpoint: String = ""
    var token: String = ""
    var httpsVerification: Boolean = true
    var debugMode: Boolean = false

    fun copyFromApi(api: Api){
        endpoint = api.httpUtil.apiUrl
        token = api.httpUtil.token
        disableHttpsVerification = api.httpUtil.disableHttpsVerification
        debugMode = api.httpUtil.testing
    }

}