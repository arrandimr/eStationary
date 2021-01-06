package com.example.estationary.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface eStationaryDAO {

    @Insert
    fun insert(eStationary: eStationary)

    @Update
    fun update(eStationary: eStationary)

    @Query("SELECT * FROM estationary")
    fun getEstationary(): LiveData<List<eStationary>>

    @Query("DELETE FROM estationary")
    fun clear()

    @Query("DELETE FROM estationary WHERE id = :eStationaryid")
    fun delete(eStationaryid: Long)
}