package com.example.finle_project.View.home.My_Posts

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.finle_project.Model.MyPost
import com.example.finle_project.R
import com.example.finle_project.util.ImageEncoding
import com.example.finle_project.viewModel.PostViewModel

class My_Posts : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)

        var imageViewMyPost = findViewById<ImageView>(R.id.imageViewMyPost)
        var imageButtonLikePost = findViewById<ImageButton>(R.id.imageButtonLikePost)
        var imageViewDelete = findViewById<ImageView>(R.id.imageViewDelete)
        var caption = findViewById<EditText>(R.id.caption)
        var accountUser = findViewById<TextView>(R.id.accountUser)
        var profilePhoto = findViewById<ImageView>(R.id.ProfilePhoto)

        var post = intent.getSerializableExtra("details") as MyPost

        imageViewMyPost.setImageBitmap(ImageEncoding.decodeBase64(post.imageUrl))
        caption.setText(post.caption)
        imageViewDelete.setOnClickListener {
            post.id?.let { id ->
                viewModel.deletePost(id).observe(this) {
                    Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}