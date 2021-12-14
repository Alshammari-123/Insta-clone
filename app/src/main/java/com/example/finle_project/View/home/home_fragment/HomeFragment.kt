package com.example.finle_project.View.home.home_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.MyPost
import com.example.finle_project.Network.PostServic
import com.example.finle_project.R
import com.example.finle_project.viewModel.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    val viewModel : PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_blank_home, container, false)


        var homeRecyclerView = v.findViewById<RecyclerView>(R.id.homeRecyclerView)
        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        //get information from mockAoi
            viewModel.getAllPosts().observe(viewLifecycleOwner,{
                homeRecyclerView.adapter = AdapterHome(it)
            })

        return v
    }

}


