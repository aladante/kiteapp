package com.jandorresteijn.kiteapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "lat") val latidude: Float?,
    @ColumnInfo(name = "long") val longitude: Float?
)

