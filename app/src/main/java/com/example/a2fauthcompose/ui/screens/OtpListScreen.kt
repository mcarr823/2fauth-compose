package com.example.a2fauthcompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.a2fauthcompose.data.classes.AbstractAccount
import com.example.a2fauthcompose.ui.components.CenteredFullscreenColumn
import com.example.a2fauthcompose.ui.components.OtpCard
import com.example.a2fauthcompose.ui.components.SearchFieldComponent
import com.example.a2fauthcompose.ui.topbar.OtpListFab
import com.example.a2fauthcompose.ui.topbar.OtpListTopBarLeft
import com.example.a2fauthcompose.ui.topbar.OtpListTopBarRight
import com.example.a2fauthcompose.util.Api
import com.example.a2fauthcompose.util.MockPreviewUtil
import com.example.a2fauthcompose.util.TokenUtil
import com.example.a2fauthcompose.viewmodels.OtpListScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OtpListScreen(
    model: OtpListScreenViewModel,
    api: Api
) {

    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    fun refresh(){
        scope.launch {
            isRefreshing = true
            model.refresh(api)
            isRefreshing = false
        }
    }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, ::refresh)
    val tokenUtil = remember {
        TokenUtil(api.httpUtil)
    }
    val accounts by remember {
        mutableStateOf(model.accounts)
    }
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchFieldComponent(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            state = textState,
            placeHolder = "Filter"
        )

        Box(
            modifier = Modifier.pullRefresh(pullRefreshState)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (!isRefreshing) {
                    items(accounts) { account ->
                        if (
                            textState.value.text.isEmpty() ||
                            (account.service != null && account.service.contains(textState.value.text, ignoreCase = true)) ||
                            account.account.contains(textState.value.text, ignoreCase = true)
                        ) {
                            OtpCard(
                                account = account,
                                util = tokenUtil
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

        }
    }

}

@Preview
@Composable
fun PreviewOtpListScreen(){
    val model = OtpListScreenViewModel()
    model.accounts = MockPreviewUtil.accountEntities.map(AbstractAccount::parse).toMutableList()
    val api = MockPreviewUtil.api
    val util = MockPreviewUtil.tokenUtil
    PreviewScreen(
        backButton = { OtpListTopBarLeft {} },
        doneButton = { OtpListTopBarRight() },
        fab = { OtpListFab() }
    ) {
        OtpListScreen(
            model = model,
            api = api
        )
    }
}