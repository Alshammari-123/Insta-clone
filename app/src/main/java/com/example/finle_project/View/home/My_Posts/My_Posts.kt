package com.example.finle_project.View.home.My_Posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.example.finle_project.Model.MyPost
import com.example.finle_project.R
import com.example.finle_project.util.ImageEncoding

class My_Posts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)

        var imageViewMyPost= findViewById<ImageView>(R.id.imageViewMyPost)
        var imageButtonLikePost=findViewById<ImageButton>(R.id.imageButtonLikePost)
        var imageButton2commentPost=findViewById<ImageButton>(R.id.imageButton2commentPost)

        var post = intent.getSerializableExtra("details") as MyPost

        imageViewMyPost.setImageBitmap(ImageEncoding.decodeBase64(post.imageUrl))

    }
}