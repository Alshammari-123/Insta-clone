package com.example.finle_project.View.home.Vido

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.ModelVideo
import com.example.finle_project.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class VideosActivity : AppCompatActivity() {

    //array list for video list
    private lateinit var videoArrayList:ArrayList<ModelVideo>
    private lateinit var adapterVideo: AdapterVideo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)
        title = "Vidoes"

        var videoViewRv = findViewById<RecyclerView>(R.id.videoViewRv)
        //function call to load videos from firbase
        loadVideosFromFirebase()

        //handle click
        var addVideoFab = findViewById<FloatingActionButton>(R.id.addVideoFab)
        addVideoFab.setOnClickListener {
            startActivity(Intent(this, AddVideosActivity::class.java))
        }}

    private fun loadVideosFromFirebase() {
        // init array befor adding data
        videoArrayList = ArrayList()


        //refernce of firebase db
        var ref = FirebaseDatabase.getInstance().getReference("videos")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list befor adding data
                videoArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val modelVideo = ds.getValue(ModelVideo::class.java)
                    // add to array list
                    videoArrayList.add(modelVideo!!)
                }
                //setup adapter
                adapterVideo = AdapterVideo(this@VideosActivity,videoArrayList)
                var videoViewRv = findViewById<RecyclerView>(R.id.videoViewRv)

                //set adapter to recyclerview
                videoViewRv.adapter= adapterVideo
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        )




    }
}