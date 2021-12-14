package com.example.finle_project.View.home.search_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.Profile
import com.example.finle_project.R

class SearchFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var v= inflater.inflate(R.layout.fragment_blank_search, container, false)

        var searchView=v.findViewById<SearchView>(R.id.searchView)
        var searchRecyclerView = v.findViewById<RecyclerView>(R.id.searchRecyclerView)

        searchRecyclerView.layoutManager=LinearLayoutManager(context)
        //searchRecyclerView.adapter=


        return v
    }

}