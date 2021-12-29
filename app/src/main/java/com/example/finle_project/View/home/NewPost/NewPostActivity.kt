package com.example.finle_project.View.home.NewPost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.finle_project.Model.MyPost
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity
import com.example.finle_project.databinding.ActivityNewPostBinding
import com.example.finle_project.util.ImageEncoding
import com.example.finle_project.viewModel.PostViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth

class NewPostActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewPostBinding
    //lateinit var currentPost:MyPost
    val vm: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)

        var myToolbar = findViewById<Toolbar>(R.id.myToolbar)


        binding.imageViewNewPost.setOnClickListener {
            //binding.imageViewNewPost.setImageBitmap(decodePicFromApi(currentPost.imageUrl))
            onActivityResult(0, 0, intent)
            ImagePicker.with(this)
                .crop()
                .compress(50)
                .maxResultSize(1080, 1080)
                .start()
        }

        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            // Use Uri object instead of File to avoid storage permissions
            binding.imageViewNewPost.setImageURI(uri)

            binding.button2AddPost.setOnClickListener {
                codApi(uri)
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun codApi(uri: Uri) {
        val encodedImage = ImageEncoding.encodeBase64(uri)
        val caption = binding.textCaption.text.toString()
        val currentPost =
            FirebaseAuth.getInstance().currentUser?.let { MyPost(caption, null, encodedImage, 0, it.uid) }

        Log.i("NewPostActivityPost", currentPost.toString())
        currentPost?.let {
            vm.createPost(it).observe(this) {
                Toast.makeText(this, "Uploaded image ${it.caption}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }


    }

}


