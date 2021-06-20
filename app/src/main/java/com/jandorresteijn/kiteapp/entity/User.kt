package com.jandorresteijn.kiteapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class User(
    @PrimaryKey() val uid: Int = 0,
    @ColumnInfo(name = "lat") val latidude: Double?,
    @ColumnInfo(name = "long") val longitude: Double?,
    @ColumnInfo(name = "hour_notification") var hour_notification: Int? = 4
)

