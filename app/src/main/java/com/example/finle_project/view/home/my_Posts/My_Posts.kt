package com.example.finle_project.view.home.my_Posts

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.finle_project.model.MyPost
import com.example.finle_project.R
import androidx.appcompat.widget.Toolbar
import com.example.finle_project.util.ImageEncoding
import com.example.finle_project.viewModel.PostViewModel

class My_Posts : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)

        var imageViewMyPost = findViewById<ImageView>(R.id.imageViewMyPost)
        var imageViewDelete = findViewById<ImageView>(R.id.imageViewDelete)
        var caption = findViewById<EditText>(R.id.caption)
        var imageViewDone = findViewById<ImageView>(R.id.imageViewDone)
        var myToolbarPost = findViewById<Toolbar>(R.id.myToolbar)

        myToolbarPost.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        myToolbarPost.setNavigationOnClickListener {
            finish()
        }


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