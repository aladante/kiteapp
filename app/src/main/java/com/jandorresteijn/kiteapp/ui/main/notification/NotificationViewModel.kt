package com.jandorresteijn.kiteapp.ui.main.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "First pick a time"
    }
    val text: LiveData<String> = _text
}
