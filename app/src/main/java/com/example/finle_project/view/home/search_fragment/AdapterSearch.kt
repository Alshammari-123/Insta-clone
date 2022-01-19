package com.example.finle_project.view.home.search_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.model.MyPost
import com.example.finle_project.R
import com.example.finle_project.util.ImageEncoding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdapterSearch(var data: List<MyPost> = listOf()) : RecyclerView.Adapter<SearchHolder>() {
    var dp = Firebase.firestore
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        context = parent.context
        var v = LayoutInflater.from(context).inflate(R.layout.row_b_search, parent, false)
        return SearchHolder(v)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {

        holder.textViewAcountSearch.text = data[position].caption
        // Picasso.get().load(data[position].imageUrl).into(holder.imageView2_search)
        holder.imageView2_search.setImageBitmap(ImageEncoding.decodeBase64(data[position].imageUrl))

    }

    override fun getItemCount(): Int {
        return data.size
    }


}

class SearchHolder(v: View) : RecyclerView.ViewHolder(v) {

    var textViewAcountSearch = v.findViewById<TextView>(R.id.textViewAcountSearch)
    var imageView2_search = v.findViewById<ImageView>(R.id.imageView2_search)

}