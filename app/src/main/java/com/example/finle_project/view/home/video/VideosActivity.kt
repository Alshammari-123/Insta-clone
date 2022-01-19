package com.example.finle_project.view.home.video

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.model.ModelVideo
import com.example.finle_project.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class VideosActivity : AppCompatActivity() {

    //array list for video list
    private lateinit var videoArrayList: ArrayList<ModelVideo>
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
        }
    }

    private fun loadVideosFromFirebase() {
        // init array befor adding data
        videoArrayList = ArrayList()

        //refernce of firebase db
        var ref = Firebase.storage.reference.child("Videos")
        ref.listAll().addOnSuccessListener { listResult ->
            listResult.items.forEach { storageReference ->
                Firebase.database.reference.child("Video")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            videoArrayList.clear()
                            snapshot.children.forEach { dataSnapshot ->
                                dataSnapshot.getValue(ModelVideo::class.java)?.let { modelVideo ->
                                    videoArrayList.add(modelVideo)
                                }
                            }

                            Toast.makeText(this@VideosActivity, "Got ${videoArrayList.size} videos", Toast.LENGTH_SHORT).show()
                            //setup adapter
                            adapterVideo = AdapterVideo(this@VideosActivity, videoArrayList)
                            var videoViewRv = findViewById<RecyclerView>(R.id.videoViewRv)

                            //set adapter to recyclerview
                            videoViewRv.adapter = adapterVideo
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                this@VideosActivity,
                                "Error: ${error.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })

            }
        }




    }

}