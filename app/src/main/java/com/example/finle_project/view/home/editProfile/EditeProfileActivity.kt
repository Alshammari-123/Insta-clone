package com.example.finle_project.view.home.editProfile

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.finle_project.model.EditUser
import com.example.finle_project.R
import com.example.finle_project.databinding.ActivityEditProfileBinding
import com.example.finle_project.util.ImageEncoding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class EditeProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var storgeRef: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var myToolbar = findViewById<Toolbar>(R.id.myToolbar)
        myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        myToolbar.setNavigationOnClickListener {
            finish()
        }

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("UsersProfile")
        binding.profliImage.setOnClickListener {
            onActivityResult(requestCode = 0, resultCode = 0, intent)
            ImagePicker.with(this)
                .crop()
                .compress(50)
                .maxResultSize(500, 600)
                .start()
        }
        binding.profliImage.setOnClickListener {

            onActivityResult(0, 0, intent)
            ImagePicker.with(this)
                .crop()
                .compress(50)
                .maxResultSize(1080, 1080)
                .start()
        }
        binding.saveBtn.setOnClickListener {

            showProgressBar()
            var firstName = binding.etFirstName.text.toString()
            var lastName = binding.etLastName.text.toString()
            var bio = binding.etBio.text.toString()

            val user = EditUser(firstName, lastName, bio)

            if (uid != null) {
                databaseRef.child(uid).setValue(user).addOnCompleteListener {

                    if (it.isSuccessful) {
                        uploadProfilePick()

                    } else {

                        hideProgressBar()
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()

                    }

                }
            }

        }


    }

    private fun uploadProfilePick() {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            println(uri)

            binding.profliImage.setImageURI(uri)
            val encodedImage = ImageEncoding.encodeBase64(uri)

            val db = Firebase.firestore
            binding.saveBtn.setOnClickListener {
                db.collection("users")
                    .document(auth.currentUser?.uid!!)
                    .update(
                        mapOf("avatar" to encodedImage,)
                    )
                    .addOnSuccessListener {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    }
            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showProgressBar() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wate)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}