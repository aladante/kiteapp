package com.jandorresteijn.kiteapp.entity

import androidx.room.*

@Dao
interface LocationDao {

    @Query("SELECT * FROM  locationTable")
    suspend fun getLocation(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)
}