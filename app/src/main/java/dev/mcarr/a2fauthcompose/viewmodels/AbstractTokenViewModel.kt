package dev.mcarr.a2fauthcompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.mcarr.a2fauthcompose.data.classes.AbstractAccount
import dev.mcarr.a2fauthcompose.data.classes.AbstractToken

class AbstractTokenViewModel<T: AbstractToken>(
    val account: AbstractAccount<T>
) : ViewModel() {

    val name = account.account
    var otp = mutableStateOf<T?>(null)

}