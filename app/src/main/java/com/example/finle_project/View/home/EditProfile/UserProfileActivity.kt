package com.example.finle_project.View.home.EditProfile

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.finle_project.Model.EditUser
import com.example.finle_project.R
import com.example.finle_project.databinding.ActivityUserProfileBinding
import com.example.finle_project.util.ImageEncoding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private lateinit var usre: EditUser
    private lateinit var uid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("UsersProfile")
        if (uid.isNotEmpty()){

          getUserData()
        }

    }

    private fun getUserData() {

        showProgressBar()
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val db = Firebase.firestore
                var encodedImage = ""
                db.collection("users").document(auth.currentUser?.uid!!)
                    .get()
                    .addOnSuccessListener {
                        encodedImage = it.getString("avatar").toString()
                        var imageBitmap = ImageEncoding.decodeBase64(encodedImage)
                        binding.profliImage.setImageBitmap(imageBitmap)
                    }

                usre = snapshot.getValue(EditUser::class.java)!!

                binding.tvFullName.setText(usre.firstName + " " + usre.lastName)
                binding.tvBio.setText(usre.bio)
                println(encodedImage)
//                binding.profliImage.setImageBitmap(imageBitmap)
                getUserProfile()

            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(this@UserProfileActivity, "Failed to get User profile data", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getUserProfile() {
        storageReference= FirebaseStorage.getInstance().reference.child("UsersProfile/$uid.jpg")
        val localFill = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFill).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localFill.absolutePath)
            binding.profliImage.setImageBitmap(bitmap)
            hideProgressBar()


        }.addOnFailureListener{
            
            hideProgressBar()
            Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show()


        }

    }

    private fun showProgressBar(){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wate)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }
}