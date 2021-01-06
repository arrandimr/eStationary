package com.example.estationary.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.estationary.db.eStationaryDAO

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val dataSource: eStationaryDAO,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(StationaryViewModel::class.java)) {
            return StationaryViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}