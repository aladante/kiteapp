package com.jandorresteijn.kiteapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationTable")
data class Location(
    @PrimaryKey() val uid: Int = 0,
    @ColumnInfo(name = "lat") val latidude: Double?,
    @ColumnInfo(name = "long") val longitude: Double?
)

