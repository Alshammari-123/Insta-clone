package com.example.finle_project.View.home.home_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.MyPost
import com.example.finle_project.R
import com.squareup.picasso.Picasso


class AdapterHome(var data: List<MyPost>):RecyclerView.Adapter<Homeholder>() {
    //var dp=Firebase.firestore
    lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Homeholder {
        context=parent.context
        var v = LayoutInflater.from(context).inflate(R.layout.rew_c_home,parent,false)
        return Homeholder(v)
    }

    override fun onBindViewHolder(holder: Homeholder, position: Int) {

        holder.textView13Comments.text= data[position].caption
        holder.textViewLikes.text= data[position].likes.toString()
        //Picasso.get().load(data[position].photoAcount).into(holder.imageView2HomeAzount)
        Picasso.get().load(data[position].imageUrl).into(holder.imageView3HomePost)

    }

    override fun getItemCount(): Int {
        return data.size
    }

}
class Homeholder(v:View):RecyclerView.ViewHolder(v){


    var imageView3HomePost=v.findViewById<ImageView>(R.id.imageView3HomePost)
    var textView13Comments=v.findViewById<TextView>(R.id.textView13Comments)
    var imageButton_like=v.findViewById<ImageButton>(R.id.imageButton_like)
    var textViewLikes=v.findViewById<TextView>(R.id.textViewLikes)

}