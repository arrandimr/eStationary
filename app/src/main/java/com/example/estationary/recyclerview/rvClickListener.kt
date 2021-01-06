package com.example.estationary.recyclerview

import android.view.View
import com.example.estationary.db.eStationary

interface rvClickListener {

    fun onRecyclerViewItemClicked(view: View, estationary: eStationary)

}