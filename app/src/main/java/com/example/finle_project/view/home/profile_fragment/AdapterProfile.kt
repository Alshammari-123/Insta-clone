package com.example.finle_project.view.home.profile_fragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.R
import com.example.finle_project.view.home.my_Posts.My_Posts
import com.example.finle_project.util.ImageEncoding
import com.google.firebase.firestore.FirebaseFirestore


class AdapterProfile(var data: MutableList<com.example.finle_project.model.MyPost>) :
    RecyclerView.Adapter<ProfileHolder>() {
    private lateinit var context: Context
    var db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {

        context = parent.context
        var v = LayoutInflater.from(context).inflate(R.layout.rew_a_profile, parent, false)
        return ProfileHolder(v)
    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {


        holder.imageView_profile.setImageBitmap(ImageEncoding.decodeBase64(data[position].imageUrl))

        holder.itemView.setOnClickListener {
            var i = Intent(holder.itemView.context, My_Posts::class.java)
            i.putExtra("details", data[position])
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class ProfileHolder(v: View) : RecyclerView.ViewHolder(v) {

    var imageView_profile = v.findViewById<ImageView>(R.id.imageView_profile)


}