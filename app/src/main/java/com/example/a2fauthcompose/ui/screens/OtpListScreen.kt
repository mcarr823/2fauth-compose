package com.example.a2fauthcompose.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.a2fauthcompose.data.classes.AbstractAccount
import com.example.a2fauthcompose.ui.components.OtpCard
import com.example.a2fauthcompose.util.MockPreviewUtil
import com.example.a2fauthcompose.util.TokenUtil
import com.example.a2fauthcompose.viewmodels.OtpListScreenViewModel

@Composable
fun OtpListScreen(
    model: OtpListScreenViewModel,
    tokenUtil: TokenUtil
) {

    val accounts = remember {
        model.accounts.map(AbstractAccount::parse)
    }

    LazyColumn{
        items(accounts){ account ->
            OtpCard(
                account = account,
                util = tokenUtil
            )
        }
    }

}

@Preview
@Composable
fun PreviewOtpListScreen(){
    val model = OtpListScreenViewModel()
    model.accounts = MockPreviewUtil.accountEntities
    val util = MockPreviewUtil.tokenUtil
    PreviewScreen {
        OtpListScreen(
            model = model,
            tokenUtil = util
        )
    }
}