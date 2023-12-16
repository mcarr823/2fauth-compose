package com.example.a2fauthcompose.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a2fauthcompose.data.entities.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * FROM `${AccountEntity.TABLE_NAME}` ORDER BY `${AccountEntity.ID}`")
    fun getAll(): List<AccountEntity>

    @Insert
    fun insert(row: AccountEntity)

    @Update
    fun update(row: AccountEntity)

    @Delete
    fun delete(row: AccountEntity)

}