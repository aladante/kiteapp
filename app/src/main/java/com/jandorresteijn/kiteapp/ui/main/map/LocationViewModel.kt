package com.jandorresteijn.kiteapp.ui.main.map

import androidx.lifecycle.ViewModel
import java.util.function.DoubleBinaryOperator

class LocationViewModel : ViewModel() {
    var lat : Double = 0.0
    var long: Double = 0.0

    fun createLocation(lat : Double, long: Double){
        this.lat = lat
        this.long = long
    }
}
