package dev.mcarr.a2fauthcompose.util

import dev.mcarr.a2fauthcompose.data.classes.AbstractAccount
import dev.mcarr.a2fauthcompose.data.classes.TotpAccount
import dev.mcarr.a2fauthcompose.data.entities.AccountEntity

object MockPreviewUtil {

    val totpAccount = TotpAccount(
        id = 0,
        groupId = null,
        service = null,
        account = "MyAccount",
        icon = null,
        digits = 6,
        algorithm = "sha1",
        secret = null,
        period = 30
    )
    val httpUtil = HttpUtil(
        apiUrl = "",
        token = ""
    )
    val api = Api(
        httpUtil = httpUtil
    )
    val tokenUtil = TokenUtil(
        httpUtil = httpUtil
    )
    val accountEntities = ArrayList<AccountEntity>().apply {
        (0..20).map {
            AccountEntity(
                id = 0,
                group_id = null,
                service = null,
                account = "My Account $it",
                icon = null,
                digits = 6,
                algorithm = "sha1",
                secret = null,
                period = 30,
                counter = 0,
                otp_type = AbstractAccount.TYPE_TOTP
            )
        }.forEach { this.add(it) }
    }

}