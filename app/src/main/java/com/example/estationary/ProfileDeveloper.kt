package com.example.estationary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.estationary.databinding.ActivityEditTransaksiBinding
import com.example.estationary.databinding.ActivityProfileDeveloperBinding
import com.example.estationary.transaksi.HomepageActivity
import kotlinx.android.synthetic.main.activity_profile_developer.*

class ProfileDeveloper : Fragment() {
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    private lateinit var binding: ActivityProfileDeveloperBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_profile_developer, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // onclick transaction
//        binding.imageButton2.setOnClickListener {
//            it.findNavController().navigate(R.id.action_activity_profile_to_activity_homepage)
//        }
//
//    }



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile_developer)
//
//        supportActionBar?.hide()
//
//        imageButton2.setOnClickListener{
//            val intent = Intent(this@profileDeveloper, HomepageActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
}