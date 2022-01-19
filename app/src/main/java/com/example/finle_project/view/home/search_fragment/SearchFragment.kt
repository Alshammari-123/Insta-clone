package com.example.finle_project.view.home.search_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.R
import com.example.finle_project.viewModel.PostViewModel

private const val TAG = "SearchFragment"

class SearchFragment : Fragment() {
    val viewModel: PostViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_blank_search, container, false)

        var searchView = v.findViewById<SearchView>(R.id.searchView).apply {
            requestFocusFromTouch()
        }
        var searchRecyclerView = v.findViewById<RecyclerView>(R.id.searchRecyclerView)
        val query = searchView.query.toString()

        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchRecyclerView.adapter = AdapterSearch()

        viewModel.getTheUser(query)
            .observe(viewLifecycleOwner) { postList ->
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val filteredList =
                            postList.filter { post ->
                                post.caption.lowercase().contains(newText!!.lowercase())
                            }
                        Log.d(TAG, "onQueryTextChange: $newText")
                        searchRecyclerView.adapter = AdapterSearch(filteredList)
                        return true
                    }

                })
            }


        return v
    }

}