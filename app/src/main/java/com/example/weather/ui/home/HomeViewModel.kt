package com.example.weather.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.R

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {}
    val text: LiveData<String> = _text

}