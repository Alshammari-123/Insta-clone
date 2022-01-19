package com.example.finle_project.view.home.home_fragment

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.model.MyPost
import com.example.finle_project.R
import com.example.finle_project.repository.PostRepository
import com.example.finle_project.util.ImageEncoding
import com.google.android.gms.maps.model.LatLng
import java.io.IOException


class AdapterHome(private var data: List<MyPost>) : RecyclerView.Adapter<Homeholder>() {
    //var dp=Firebase.firestore
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Homeholder {
        context = parent.context
        var v = LayoutInflater.from(context).inflate(R.layout.rew_c_home, parent, false)
        return Homeholder(v)
    }

    override fun onBindViewHolder(holder: Homeholder, position: Int) {
        val repository = PostRepository()

        holder.textView13Comments.text = data[position].caption
        holder.textViewLikes.text = data[position].likes.toString()
        //Picasso.get().load(data[position].photoAcount).into(holder.imageView2HomeAzount)
        val decodedBitmap = ImageEncoding.decodeBase64(data[position].imageUrl)
        holder.imageView3HomePost.setImageBitmap(decodedBitmap)
//        Picasso.get().load(data[position].imageUrl).into(holder.imageView3HomePost)

        holder.imageButton_like.setOnClickListener {
            val newPost = data[position]
            newPost.likes++
            notifyItemChanged(position)
            repository.updatePost(newPost)
        }

        val postLocation = data[position].userLocation

        if (postLocation != null) {
            val splitLocation = postLocation.split(",")
            val latLng = LatLng(splitLocation[0].toDouble(), splitLocation[1].toDouble())
            holder.postLocationTextView.text = getAddressFromLocation(latLng)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getAddressFromLocation(location: LatLng): String {
        val geocoder = Geocoder(context)
        var address: String? = null

        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isNotEmpty()) {
                address = addresses[0].getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return address ?: "No address found"
    }
}

class Homeholder(v: View) : RecyclerView.ViewHolder(v) {

    var postLocationTextView = v.findViewById<TextView>(R.id.postLocationTextView)
    var imageView3HomePost = v.findViewById<ImageView>(R.id.imageView3HomePost)
    var textView13Comments = v.findViewById<TextView>(R.id.textView13Comments)
    var imageButton_like = v.findViewById<ImageButton>(R.id.imageButton_like)
    var textViewLikes = v.findViewById<TextView>(R.id.textViewLikes)

}