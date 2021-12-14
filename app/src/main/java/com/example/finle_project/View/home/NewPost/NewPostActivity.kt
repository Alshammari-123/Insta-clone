package com.example.finle_project.View.home.NewPost

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.finle_project.Model.MyPost
import com.example.finle_project.databinding.ActivityNewPostBinding
import com.example.finle_project.viewModel.PostViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream

class NewPostActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewPostBinding
    val vm: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)


//        vm.getAllPosts().observe(this,{
//            currentPost= it[0]//
//
//            if (currentPost.imageUrl.length>120){
//                binding.imageViewNewPost.setImageBitmap(decodePicFromApi(currentPost.imageUrl))
//            } else {
//                Picasso.get().load(currentPost.imageUrl).into(binding.imageViewNewPost)
//            }
//            binding.textView6name.text=currentPost.caption
//
//
//        })
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
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

        // initialize byte stream
        val stream = ByteArrayOutputStream()

        // compress Bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        // Initialize byte array
        val bytes: ByteArray = stream.toByteArray()
        // get base64 encoded string
        val encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT)
        // set encoded text on textview
        println(encodedImage)
        val caption = binding.textCaption.text.toString()
        val currentPost = MyPost(caption, null, encodedImage, 0)
        Log.i("NewPostActivityPost", currentPost.toString())
        vm.createPost(currentPost)
    }

    fun decodePicFromApi(encodedString: String): Bitmap {

        println(encodedString)
        val imageBytes = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        return decodedImage
    }


}


