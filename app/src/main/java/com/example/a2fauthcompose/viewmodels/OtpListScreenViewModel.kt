package com.example.a2fauthcompose.viewmodels

import androidx.lifecycle.ViewModel
import com.example.a2fauthcompose.data.entities.AccountEntity

class OtpListScreenViewModel : ViewModel() {

    var accounts: List<AccountEntity> = listOf()

}