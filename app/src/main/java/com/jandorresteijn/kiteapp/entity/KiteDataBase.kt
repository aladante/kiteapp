package com.jandorresteijn.kiteapp.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class KiteDataBase: RoomDatabase() {

    abstract fun UserDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "KITEDB"

        @Volatile
        private var kiteDBInstance: KiteDataBase? = null

        fun getDatabase(context: Context): KiteDataBase? {
            if (kiteDBInstance== null) {
                synchronized(KiteDataBase::class.java) {
                    if (kiteDBInstance== null) {
                        kiteDBInstance= Room.databaseBuilder(
                            context.applicationContext,
                            KiteDataBase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return kiteDBInstance
        }
    }

}