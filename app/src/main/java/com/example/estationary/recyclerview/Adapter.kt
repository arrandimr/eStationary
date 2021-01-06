package com.example.estationary.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.estationary.R
import com.example.estationary.databinding.HomepageListBinding
import com.example.estationary.db.eStationary
import com.example.estationary.utils.convertLongToDateString
import com.example.estationary.utils.rupiah

class Adapter (
    private val stationary: List<eStationary>
) : RecyclerView.Adapter<Adapter.eStationaryViewHolder>() {

    var listener: rvClickListener? = null

    inner class eStationaryViewHolder(
        val recyclervieweStationaryBinding: HomepageListBinding
    ) : RecyclerView.ViewHolder(recyclervieweStationaryBinding.root)

    override fun getItemCount(): Int {
        return stationary.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = eStationaryViewHolder (
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.homepage_list, parent, false)
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: eStationaryViewHolder, position: Int) {
        holder.recyclervieweStationaryBinding.tvDate.text = convertLongToDateString(stationary[position].tanggal)
        holder.recyclervieweStationaryBinding.tvNamaPelanggan.text = stationary[position].nama
        holder.recyclervieweStationaryBinding.tvIdTransaksi.text = stationary[position].id.toString()
        holder.recyclervieweStationaryBinding.tvHarga.text = rupiah(stationary[position].totalharga)

        holder.recyclervieweStationaryBinding.listDataTransaksi.setOnClickListener {
            Log.i("clicked", "oke bisa")
            listener?.onRecyclerViewItemClicked(it, stationary[position])
        }
    }
}