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
        val users: List<User>? = userDao.getUser()
        Log.e("what", users.toString())

        if (users == null || users.size < 1) {
            var longitude: Double = 3.9041
            var latitude: Double = 52.367
            var user: User = User(latidude = latitude, longitude = longitude)
            return user
        }

        return users.get(0)
    }

    fun addUser(user: User) {
        userDao.insertUser(user)
    }
}