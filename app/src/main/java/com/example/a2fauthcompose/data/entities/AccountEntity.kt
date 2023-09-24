package com.example.a2fauthcompose.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a2fauthcompose.data.classes.Account

@Entity(tableName = AccountEntity.TABLE_NAME)
class AccountEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = GROUP_ID)
    val group_id: Int?,
    @ColumnInfo(name = SERVICE)
    val service: String?,
    @ColumnInfo(name = ACCOUNT)
    val account: String,
    @ColumnInfo(name = ICON)
    val icon: String?, //filename
    @ColumnInfo(name = OTP_TYPE)
    val otp_type: String,
    @ColumnInfo(name = DIGITS)
    val digits: Int,
    @ColumnInfo(name = ALGORITHM)
    val algorithm: String,
    @ColumnInfo(name = SECRET)
    val secret: String?,
    @ColumnInfo(name = PERIOD)
    val period: Int?,
    @ColumnInfo(name = COUNTER)
    val counter: Int?,
) {

    constructor(acc: Account) : this(
        id = acc.id,
        group_id = acc.group_id,
        service = acc.service,
        account = acc.account,
        icon = acc.icon,
        otp_type = acc.otp_type,
        digits = acc.digits,
        algorithm = acc.algorithm,
        secret = acc.secret,
        period = acc.period,
        counter = acc.counter
    )

    companion object{
        const val TABLE_NAME = "AccountEntity"
        const val ID = "ae_id"
        const val GROUP_ID = "ae_group_id"
        const val SERVICE = "ae_service"
        const val ACCOUNT = "ae_account"
        const val ICON = "ae_icon"
        const val OTP_TYPE = "ae_otp_type"
        const val DIGITS = "ae_digits"
        const val ALGORITHM = "ae_algorithm"
        const val SECRET = "ae_secret"
        const val PERIOD = "ae_period"
        const val COUNTER = "ae_counter"
    }

}