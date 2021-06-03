package com.jandorresteijn.kiteapp.entity

import android.content.Context

class LocationRepository(context: Context) {

    private var locationDao: LocationDao

    init {
        val reminderRoomDatabase =
            KiteDataBase.getDatabase(context)
        locationDao = reminderRoomDatabase!!.locationDao()
    }

    suspend fun getLocation(): List<Location> {
        return locationDao.getLocation()
    }

    fun addLocation(location: Location) {
        locationDao.insertLocation(location)
    }
}