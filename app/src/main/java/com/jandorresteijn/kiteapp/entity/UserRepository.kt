package com.jandorresteijn.kiteapp.entity

import android.content.Context
import android.util.Log

class UserRepository(context: Context) {

    private var userDao: UserDao

    init {
        val reminderRoomDatabase =
            KiteDataBase.getDatabase(context)
        userDao = reminderRoomDatabase!!.UserDao()
    }

    suspend fun getUser(): User {
        val users: List<User> = userDao.getUser()
        Log.e("what", users.toString())

        if (users.isEmpty()) {
            val longitude = 3.9041
            val latitude = 52.367
            return User(latidude = latitude, longitude = longitude)
        }

        return users[0]
    }

    fun addUser(user: User) {
        userDao.insertUser(user)
    }
}