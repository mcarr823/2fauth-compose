package com.example.a2fauthcompose.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a2fauthcompose.data.daos.AccountDao
import com.example.a2fauthcompose.data.entities.AccountEntity

@Database(entities = [
    AccountEntity::class
], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object{

        private const val DATABASE_NAME = "2fauth-compose-android"

        private var database: AppDb? = null

        fun getDatabase(c: Context): AppDb =
            database ?: synchronized(this){
                Room.databaseBuilder(c.applicationContext, AppDb::class.java, DATABASE_NAME)
                    .build()
                    .also { database = it }
            }

    }

}