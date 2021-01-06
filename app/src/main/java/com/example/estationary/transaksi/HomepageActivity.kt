package com.example.estationary.transaksi

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estationary.*
import com.example.estationary.databinding.ActivityHomepageBinding
import com.example.estationary.db.eStationary
import com.example.estationary.db.eStationaryDB
import com.example.estationary.recyclerview.Adapter
import com.example.estationary.recyclerview.rvClickListener
import com.example.estationary.viewmodel.StationaryViewModel
import com.example.estationary.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class HomepageActivity : Fragment(),
        rvClickListener {

        private lateinit var binding: ActivityHomepageBinding
        private lateinit var myViewModel: StationaryViewModel

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            judul()
            setHasOptionsMenu(true)
            binding = DataBindingUtil.inflate(inflater, R.layout.activity_homepage, container, false)

//            val application = requireNotNull(this.activity).application
//            myViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(StationaryViewModel::class.java)

            // Inflate the layout for this fragment
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            getViewModel().estationary.observe(viewLifecycleOwner, Observer {
                val adapter = Adapter(it)
                val recyclerView = binding.rvEstationary
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

                // set listener
                adapter.listener = this
            })

            binding.fabAddTransaksi.setOnClickListener {
                it.findNavController().navigate(R.id.action_activity_homepage_to_activity_add_transaksi)
            }
        }

        private fun getViewModel(): StationaryViewModel {
            // get viewmodel
            val application = requireNotNull(this.activity).application
            val dataSource = eStationaryDB.getInstance(application).estationaryDAO
            val viewModelFactory = ViewModelFactory(dataSource, application)
            return ViewModelProvider(this, viewModelFactory).get(StationaryViewModel::class.java)
        }

        override fun onRecyclerViewItemClicked(view: View, estationary: eStationary) {
            val bundle = Bundle()
            bundle.putLong("id", estationary.id)
            bundle.putString("nama", estationary.nama)
            bundle.putString("kodebarang", estationary.kodebarang)
            bundle.putDouble("jumlah", estationary.jumlah)
            bundle.putString("namabarang", estationary.namabarang)
            bundle.putDouble("totalharga", estationary.totalharga)
            bundle.putDouble("kembalian", estationary.kembalian)
            bundle.putLong("tanggal", estationary.tanggal)

            when(view.id) {
                R.id.list_data_transaksi -> {
                    view.findNavController().navigate(R.id.action_activity_homepage_to_activity_edit_transaksi, bundle)
                }
            }

        }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            super.onCreateOptionsMenu(menu, inflater)
            inflater.inflate(R.menu.delete, menu)
            inflater.inflate(R.menu.info,menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            return when(item.itemId) {
                R.id.item_delete -> {
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle(getString(R.string.delete_confirmation))
                        it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                            getViewModel().onClickClear()
                            Snackbar.make(requireView(), getString(R.string.success_delete), Snackbar.LENGTH_SHORT).show()
                        }
                    }.create().show()
                    true
                }
                R.id.item_info -> {
                    binding.root.findNavController().navigate(R.id.action_activity_homepage_to_activity_profile)
                    //profil()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }

//    private fun profil() {
//        Navigation.findNavController(this.activity,R.id.navigation).navigate(R.id.action_activity_profile_to_activity_homepage)
//    }

        private fun judul() {
            val getActivity = activity!! as HomeActivity
            getActivity.supportActionBar?.title = "e-Stationary"
        }
    }

