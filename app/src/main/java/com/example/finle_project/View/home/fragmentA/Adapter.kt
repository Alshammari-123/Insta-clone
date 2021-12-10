package com.example.finle_project.View.home.fragmentA

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.Profile
import com.example.finle_project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class AdapterProfile (var data:MutableList<Profile>):RecyclerView.Adapter<ProfileHolder>() {
private lateinit var context: Context
var db =FirebaseAuth.getInstance()
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {

         context=parent.context
         var v= LayoutInflater.from(context).inflate(R.layout.rew_a_profile,parent,false)
    return ProfileHolder(v)
     }

     override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
         Picasso.get().load(data[position].photo).into(holder.imageView_profile)
     }

     override fun getItemCount(): Int {
         return data.size
     }
 }
class ProfileHolder(v:View):RecyclerView.ViewHolder(v){

    var imageView_profile=v.findViewById<ImageView>(R.id.imageView_profile)



}