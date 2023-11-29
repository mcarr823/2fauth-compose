package com.example.a2fauthcompose.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.a2fauthcompose.data.classes.AbstractAccount
import com.example.a2fauthcompose.data.databases.AppDb
import com.example.a2fauthcompose.data.entities.AccountEntity
import com.example.a2fauthcompose.data.exceptions.Auth2FException401
import com.example.a2fauthcompose.data.exceptions.Auth2FException403
import com.example.a2fauthcompose.util.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OtpListScreenViewModel : ViewModel() {

    var accounts = mutableListOf<AbstractAccount>()

    /**
     * @return Error message, or empty string if successful
     * */
    suspend fun refresh(api: Api): String =
        try {
            val newAccounts = api.getAll2FaAccounts(withSecret = false).map { AccountEntity(it) }
            accounts.clear()
            newAccounts.map(AbstractAccount::parse).let(accounts::addAll)
            //val db = AppDb.getDatabase()
            //TODO: save in database
            ""
        } catch (e: Auth2FException401){
            e.printStackTrace()
            "Authentication failed"
        } catch (e: Auth2FException403){
            e.printStackTrace()
            "Access forbidden"
        } catch (e: Exception) {
            e.printStackTrace()
            "No internet connection"
        }

}