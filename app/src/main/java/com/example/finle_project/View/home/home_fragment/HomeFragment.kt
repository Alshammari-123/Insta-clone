package com.example.finle_project.View.home.home_fragment

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.R
import com.example.finle_project.databinding.FragmentBlankHomeBinding
import com.example.finle_project.viewModel.PostViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentBlankHomeBinding
    val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_blank_home, container, false)
        binding = FragmentBlankHomeBinding.inflate(layoutInflater,container,false)

        recyclerView = v.findViewById(R.id.homeRecyclerView)

        return v
    }

    override fun onStart() {
        super.onStart()

        recyclerView.layoutManager = LinearLayoutManager(context)
        //get information from mockAoi
        viewModel.getAllPosts().observe(viewLifecycleOwner, {
            binding.homeProgressBar
            binding.homeProgressBar.visibility =View.GONE

            recyclerView.adapter = AdapterHome(it)




        })
    }

}


