package com.jandorresteijn.kiteapp.entity

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM  userTable")
    suspend fun getUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}