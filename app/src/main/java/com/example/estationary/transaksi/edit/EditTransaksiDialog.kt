package com.example.estationary.transaksi.edit

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.estationary.R
import com.example.estationary.db.eStationary
import com.example.estationary.db.eStationaryDB
import com.example.estationary.utils.rupiah
import com.example.estationary.viewmodel.StationaryViewModel
import com.example.estationary.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_edit_transaksi_dialog.*


@Suppress("SpellCheckingInspection")
class EditTransaksiDialog (
    private val dataeStationary: eStationary
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_edit_transaksi_dialog, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog_MinWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditText()

        // call view model
        val application = requireNotNull(this.activity).application
        val dataSource = eStationaryDB.getInstance(application).estationaryDAO
        val viewModelFactory = ViewModelFactory(dataSource, application)
        val StationaryViewModel = ViewModelProvider(this, viewModelFactory).get(StationaryViewModel::class.java)

        etd_btn_proses.setOnClickListener {
            StationaryViewModel.onClickUpdate(dataeStationary)
            this.dismiss()
            this.findNavController().popBackStack()
        }
    }

    private fun setEditText() {
        etd_nama_pelanggan.setText(dataeStationary.nama)
        etd_kode_barang.setText(dataeStationary.kodebarang)
        etd_jumlah_barang.setText(dataeStationary.jumlah.toString())
        etd_nama_produk.setText(dataeStationary.namabarang)
        etd_total_harga.setText(rupiah(dataeStationary.totalharga))
        etd_total_bayar.setText(rupiah(dataeStationary.totalbayar))
        etd_kembalian.setText(rupiah(dataeStationary.kembalian))
    }

}
