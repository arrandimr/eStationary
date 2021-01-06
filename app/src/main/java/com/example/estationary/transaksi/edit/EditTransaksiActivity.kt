package com.example.estationary.transaksi.edit
import com.example.estationary.transaksi.edit.EditTransaksiDialog
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.estationary.HomeActivity
import com.google.android.material.snackbar.Snackbar
import com.example.estationary.MainActivity

import com.example.estationary.R
import com.example.estationary.db.eStationary
import com.example.estationary.db.eStationaryDB
import com.example.estationary.databinding.ActivityEditTransaksiBinding
import com.example.estationary.utils.rupiah
import com.example.estationary.viewmodel.StationaryViewModel
import com.example.estationary.viewmodel.ViewModelFactory

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class EditTransaksiActivity : Fragment() {

    private lateinit var binding: ActivityEditTransaksiBinding
    private var harga = 0.0
    private var kembalian = 0.0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        judul()
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_edit_transaksi, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set input text
        binding.editNamaPelanggan.setText(getBundle()!!.nama)
        binding.editKodeProduk.setText(getBundle()!!.kodebarang)
        binding.editNamaProduk.setText(getBundle()!!.namabarang)
        binding.editJumlahProduk.setText(getBundle()!!.jumlah.toString())
        binding.editHargaProduk.setText(getBundle()!!.harga.toString())
        binding.editTotalHarga.setText("${getBundle()!!.totalharga.toInt()}")
        binding.editTotalBayar.setText("${getBundle()!!.totalbayar.toInt()}")

        // onclick transaction
        binding.editBtnProses.setOnClickListener {
            inputCheck()
        }

        binding.editJumlahProduk.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var harga = binding.editHargaProduk.text.toString().toDouble()
                var jumlah = binding.editJumlahProduk.text.toString().toDouble()

                var totalharga = harga*jumlah
                binding.editTotalHarga.setText(totalharga.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

    private fun getBundle(): eStationary? {
        if (arguments != null) {
            val id = arguments!!.getLong("id")
            val nama = arguments!!.getString("nama")
            val kodebarang = arguments!!.getString("kodebarang")
            val jumlah = arguments!!.getDouble("jumlah")
            val namabarang = arguments!!.getString("namabarang")
            val harga = arguments!!.getDouble("harga")
            val totalharga = arguments!!.getDouble("totalharga")
            val bayar = arguments!!.getDouble("bayar")
            val kembalian = arguments!!.getDouble("kembalian")
            val tanggal = arguments!!.getLong("tanggal")

            return eStationary(id, nama.toString(), kodebarang.toString(), namabarang.toString(), jumlah,harga, totalharga, bayar, kembalian, tanggal)
        } else {
            return null
        }
    }

    private fun inputCheck() {
        when {
            binding.editNamaPelanggan.text.trim().isEmpty() -> {
                binding.inputLayoutNama.error = getString(R.string.null_field, "Nama pelanggan")
            }
            binding.editKodeProduk.text.trim().isEmpty() -> {
                binding.inputLayoutKode.error = getString(R.string.null_field, "Kode produk")
            }
            binding.editHargaProduk.text.trim().isEmpty() -> {
                binding.inputLayoutTotalHarga.error = getString(R.string.null_field, "Total harga")
            }
            binding.editTotalHarga.text.trim().isEmpty() -> {
                binding.inputLayoutTotalBayar.error = getString(R.string.null_field, "Total bayar")
            }
            binding.editHargaProduk.text.trim().isEmpty() -> {
                binding.inputLayoutHarga.error = getString(R.string.null_field, "Total bayar")
            }
            binding.editJumlahProduk.text.trim().isEmpty() -> {
                binding.inputLayoutJumlah.error = getString(R.string.null_field, "Total bayar")
            }
            binding.editTotalBayar.text.trim().isEmpty() -> {
                binding.inputLayoutTotalBayar.error = getString(R.string.null_field, "Total bayar")
            }
            else -> doProcess()
        }
    }

    private fun doProcess() {
        val totalBayar = binding.editTotalBayar.text.toString().toDouble()
        val kodebarang = binding.editKodeProduk.text.toString()
        val jumlah = binding.editJumlahProduk.text.toString().toDouble()
        val namabarang = binding.editNamaProduk.text.toString()
        val harga = binding.editHargaProduk.text.toString().toDouble()
        val totalHarga = binding.editTotalHarga.text.toString().toDouble()
        val tanggal = System.currentTimeMillis()


        if (hitungTransaksi(totalBayar, totalHarga)) {
            val nama = binding.editNamaPelanggan.text.toString()
            val kembalian = kembalian

            val stationary = eStationary(getBundle()!!.id, nama, kodebarang, namabarang, jumlah, harga, totalHarga, totalBayar, kembalian, tanggal)
            EditTransaksiDialog(stationary).show(childFragmentManager, "")
        } else {
            binding.inputLayoutTotalBayar.error = getString(R.string.uang_kurang)
        }
    }

    private fun hitungTransaksi(jumlahUang: Double, totalHarga: Double): Boolean {
        return when {
            jumlahUang >= totalHarga -> {
                kembalian = (jumlahUang - totalHarga)
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete, menu)
        inflater.inflate(R.menu.refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // call view model
        val application = requireNotNull(this.activity).application
        val dataSource = eStationaryDB.getInstance(application).estationaryDAO
        val viewModelFactory = ViewModelFactory(dataSource, application)
        val barberShopViewModel = ViewModelProvider(this, viewModelFactory).get(StationaryViewModel::class.java)

        return when(item.itemId) {
            R.id.item_delete -> {

                AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.delete_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        barberShopViewModel.onClickDelete(getBundle()!!.id)
                        this.findNavController().popBackStack()
                        Snackbar.make(requireView(), getString(R.string.success_delete), Snackbar.LENGTH_SHORT).show()
                    }
                }.create().show()

                true
            }
            R.id.item_reset -> {
                reset()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun reset() {
        binding.editNamaPelanggan.setText("")
        binding.inputLayoutNama.error = null
        binding.editKodeProduk.setText("")
        binding.inputLayoutKode.error = null
        binding.editNamaProduk.setText("")
        binding.inputLayoutNamaproduk.error = null
        binding.editHargaProduk.setText("")
        binding.inputLayoutHarga.error = null
        binding.editTotalHarga.setText("")
        binding.inputLayoutTotalHarga.error = null
        binding.editTotalBayar.setText("")
        binding.inputLayoutTotalBayar.error = null
    }

    private fun judul() {
        val getActivity = activity!! as HomeActivity
        getActivity.supportActionBar?.title = "Transaksi"
    }
}