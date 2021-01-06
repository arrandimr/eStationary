package com.example.estationary.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estationary")
data class eStationary(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "nama")
    var nama: String,

    @ColumnInfo(name = "kodebarang")
    var kodebarang: String,

    @ColumnInfo(name = "namabarang")
    var namabarang: String,

    @ColumnInfo(name = "jumlah")
    var jumlah: Double,

    @ColumnInfo(name = "harga")
    var harga: Double,

    @ColumnInfo(name = "totalharga")
    var totalharga: Double,

    @ColumnInfo(name = "totalbayar")
    var totalbayar: Double,

    @ColumnInfo(name = "kembalian")
    var kembalian: Double,

    @ColumnInfo(name = "tanggal")
    var tanggal: Long = System.currentTimeMillis()
)
