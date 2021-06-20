package com.jandorresteijn.kiteapp.entity

import android.content.Context

class UserRepository(context: Context) {

    private var userDao: UserDao

    init {
        val reminderRoomDatabase =
            KiteDataBase.getDatabase(context)
        userDao = reminderRoomDatabase!!.UserDao()
    }

    suspend fun getUser(): List<User> {
        return userDao.getUser()
    }

    fun addUser(location: User) {
        userDao.insertUser(location)
    }
}