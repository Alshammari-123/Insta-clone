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
        var imageViewDone = findViewById<ImageView>(R.id.imageViewDone)
        var myToolbarPost = findViewById<Toolbar>(R.id.myToolbarPost)

//        myToolbarPost.setOnMenuItemClickListener {
//            when(it.itemId){
//                R.id.back ->{
//                    finish()
//                    true
//                }
//
//                else -> {true}
//            }
//        }

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

        imageViewDone.setOnClickListener {
            post.id?.let { id ->
                val updatedPost = post.apply {
                    this.caption = caption.text.toString()
                }
                viewModel.updatePost(updatedPost).observe(this) {
                    Toast.makeText(this, "Update Done", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    }
}