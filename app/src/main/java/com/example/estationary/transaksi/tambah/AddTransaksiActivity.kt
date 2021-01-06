package com.example.estationary.transaksi.tambah

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.estationary.HomeActivity
import com.example.estationary.R
import com.example.estationary.databinding.ActivityAddTransaksiBinding
import com.example.estationary.db.eStationary
import com.example.estationary.transaksi.edit.EditTransaksiDialog
import com.example.estationary.utils.rupiah
import kotlinx.android.synthetic.main.activity_add_transaksi.*
import kotlinx.android.synthetic.main.activity_edit_transaksi_dialog.*
import java.util.ResourceBundle.getBundle

@Suppress("SpellCheckingInspection")
class AddTransaksiActivity : Fragment() {

    private lateinit var binding: ActivityAddTransaksiBinding
    private var harga: Double = 0.0
    private var jumlah: Double = 0.0
    private var jumlahUang: Double = 0.0
    private var kembalian = 0.0

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.activity_add_transaksi, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTambah.setOnClickListener {
            inputCheck()
        }

        binding.addJumlahBeli.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var harga = binding.addHargaProduk.text.toString().toDouble()
                var jumlah = binding.addJumlahBeli.text.toString().toDouble()

                var totalharga = harga*jumlah
                binding.addTotalHarga.setText(totalharga.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun inputCheck() {
        when {
            binding.addNamaPelanggan.text.trim().isEmpty() -> {
                binding.inputLayoutNama.error = getString(R.string.null_field, "Nama pelanggan")
            }
            binding.addKodeProduk.text.trim().isEmpty() -> {
                binding.inputLayoutKode.error = getString(R.string.null_field, "Kode produk")
            }
            binding.addHargaProduk.text.trim().isEmpty() -> {
                binding.inputLayoutTotalHarga.error = getString(R.string.null_field, "Total harga")
            }
            binding.addTotalBayar.text.trim().isEmpty() -> {
                binding.inputLayoutTotalBayar.error = getString(R.string.null_field, "Total bayar")
            }
            binding.addHargaProduk.text.trim().isEmpty() -> {
                binding.inputLayoutHarga.error = getString(R.string.null_field, "Total bayar")
            }
            binding.addJumlahBeli.text.trim().isEmpty() -> {
                binding.inputLayoutJumlah.error = getString(R.string.null_field, "Total bayar")
            }
            binding.addTotalBayar.text.trim().isEmpty() -> {
                binding.inputLayoutTotalBayar.error = getString(R.string.null_field, "Total bayar")
            }
            else -> doProcess()
        }
    }

    private fun doProcess() {
        val totalBayar = binding.addTotalBayar.text.toString().toDouble()
        val kodebarang = binding.addKodeProduk.text.toString()
        val jumlah = binding.addJumlahBeli.text.toString().toDouble()
        val namabarang = binding.addNamaProduk.text.toString()
        val harga = binding.addHargaProduk.text.toString().toDouble()
        val totalHarga = binding.addTotalHarga.text.toString().toDouble()
        val tanggal = System.currentTimeMillis()


        if (hitungTransaksi(totalBayar, totalHarga)) {
            val nama = binding.addNamaPelanggan.text.toString()
            val kembalian = kembalian

            val stationary = eStationary(0, nama, kodebarang, namabarang, jumlah, harga, totalHarga, totalBayar, kembalian, tanggal)
            AddTransaksiDialog(stationary).show(childFragmentManager, "")
        } else {
            binding.inputLayoutTotalBayar.error = getString(R.string.uang_kurang)
        }
    }

    private fun hitungTransaksi(jumlahUang: Double, totalHarga: Double): Boolean {
        val totalBayar = binding.addTotalBayar.text.toString().toDouble()

        return when {
            jumlahUang >= harga -> {
                kembalian = totalBayar - totalHarga
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.item_reset -> {
                reset()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun reset() {
        binding.addNamaPelanggan.setText("")
        binding.inputLayoutNama.error = null
        binding.addKodeProduk.setText("")
        binding.inputLayoutKode.error = null
        binding.addNamaProduk.setText("")
        binding.inputLayoutNamaproduk.error = null
        binding.addHargaProduk.setText("")
        binding.inputLayoutHarga.error = null
        binding.addTotalHarga.setText("")
        binding.inputLayoutTotalHarga.error = null
        binding.addTotalBayar.setText("")
        binding.inputLayoutTotalBayar.error = null
    }

    private fun judul() {
        val getActivity = activity!! as HomeActivity
        getActivity.supportActionBar?.title = "Transaksi"
    }

}