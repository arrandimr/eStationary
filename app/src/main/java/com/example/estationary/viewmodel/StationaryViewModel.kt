package com.example.estationary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.estationary.db.eStationary
import com.example.estationary.db.eStationaryDAO
import kotlinx.coroutines.*

class StationaryViewModel (
    val database: eStationaryDAO,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    public val estationary : LiveData<List<eStationary>>

    init {
        estationary = database.getEstationary()
    }

    fun onClickInsert(stationary: eStationary) {
        uiScope.launch {
            insert(stationary)
        }
    }

    private suspend fun insert(stationary: eStationary) {
        withContext(Dispatchers.IO) {
            database.insert(stationary)
        }
    }

    fun onClickUpdate(stationary: eStationary) {
        uiScope.launch {
            update(stationary)
        }
    }

    private suspend fun update(stationary: eStationary) {
        withContext(Dispatchers.IO) {
            database.update(stationary)
        }
    }

    fun onClickClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onClickDelete(id: Long) {
        uiScope.launch {
            delete(id)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.delete(id)
        }
    }
}
