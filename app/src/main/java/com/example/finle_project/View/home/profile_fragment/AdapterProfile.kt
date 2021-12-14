package com.example.finle_project.View.home.profile_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class AdapterProfile(var data: MutableList<com.example.finle_project.Model.MyPost>):RecyclerView.Adapter<ProfileHolder>() {
private lateinit var context: Context
var db=FirebaseFirestore.getInstance()

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {

         context=parent.context
         var v= LayoutInflater.from(context).inflate(R.layout.rew_a_profile,parent,false)
    return ProfileHolder(v)
     }

     override fun onBindViewHolder(holder: ProfileHolder, position: Int) {


         Picasso.get().load(data[position].imageUrl).into(holder.imageView_profile)

      //   holder.itemView.setOnClickListener {
           //  var i = Intent(holder.itemView.context,MyPost::class.java)
           //  i.putExtra("details",data[position])
            // holder.itemView.context.startActivity(i)
      //   }
     }

     override fun getItemCount(): Int {
         return data.size
     }
 }
class ProfileHolder(v:View):RecyclerView.ViewHolder(v){

    var imageView_profile=v.findViewById<ImageView>(R.id.imageView_profile)



}