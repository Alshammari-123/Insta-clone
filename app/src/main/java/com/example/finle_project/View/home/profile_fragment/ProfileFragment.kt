package com.example.finle_project.View.home.profile_fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finle_project.Model.EditUser
import com.example.finle_project.Model.MyPost
import com.example.finle_project.R
import com.example.finle_project.View.home.EditProfile.EditeProfileActivity
import com.example.finle_project.View.home.MainActivity.MainActivity
import com.example.finle_project.databinding.FragmentBlankProfileBinding
import com.example.finle_project.util.ImageEncoding
import com.example.finle_project.viewModel.PostViewModel
import com.example.finle_project.viewModel.UserViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentBlankProfileBinding
    private lateinit var imageView4EditProfile: ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
   // private lateinit var dialog: Dialog
    private lateinit var usre: EditUser
    private lateinit var uid: String
    private lateinit var editProfile:Button

    val vm: PostViewModel by viewModels()
    val viewModel: PostViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBlankProfileBinding.inflate(layoutInflater, container, false)

        binding.editProfile.setOnClickListener {
            var i = Intent(binding.root.context,EditeProfileActivity::class.java)
            startActivity(i)
        }



        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("UsersProfile")
        if (uid.isNotEmpty()){

            getUserData()
        }


        //profileRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.ProfileRecyclerView.layoutManager = GridLayoutManager(this.context, 3)
        FirebaseAuth.getInstance().currentUser?.let { user ->
            viewModel.getMyPost(user.uid).observe(viewLifecycleOwner, { postList ->
                Log.d(TAG, "onCreateView: $postList")
                binding.profileProgressBar
                binding.profileProgressBar.visibility = View.GONE
                binding.ProfileRecyclerView.adapter =
                    AdapterProfile(postList as MutableList<MyPost>)
            })
        }

//        binding.buttonEditProfile.setOnClickListener {
//            val customEditDialog = AlertDialog.Builder(context).create()
//            var v = LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null, false)
//            var db = Firebase.firestore
//            var auth = Firebase.auth
//            customEditDialog.setView(v)
//            customEditDialog.show()
//
//            imageView4EditProfile = v.findViewById(R.id.imageView4EditProfile)
//            var editTextTextPersonName = v.findViewById<EditText>(R.id.editTextTextPersonName)
//            var editTextUsername = v.findViewById<EditText>(R.id.editTextUsername)
//            var button6Cancel = v.findViewById<Button>(R.id.button6Cancel)
//            var button7Done = v.findViewById<Button>(R.id.button7Done)
//            var textView9ChangePhotoProfilEdit =
//                v.findViewById<TextView>(R.id.textView9ChangePhotoProfilEdit)
//
//            textView9ChangePhotoProfilEdit.setOnClickListener {
//                //binding.imageViewNewPost.setImageBitmap(decodePicFromApi(currentPost.imageUrl))
//                // onActivityResult(0, 0, activity)
//                ImagePicker.with(this)
//                    .crop()
//                    .compress(50)
//                    .maxResultSize(1080, 1080)
//                    .start()
//            }
//
//            //update data for profile
//            button7Done.setOnClickListener {
//                val fullname = editTextTextPersonName.text.toString()
//                val email = editTextUsername.text.toString()
//
//                Toast.makeText(
//                    this.context,
//                    auth.currentUser?.uid.toString(),
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//                db.collection("users").document(auth.currentUser?.uid.toString()).update(
//                    mapOf(
//                        "FullName" to fullname,
//                        "email" to email
//                    )
//
//                ).addOnSuccessListener {
//                    Toast.makeText(
//                        activity,
//                        "Fullname updated to $fullname",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
////                    binding.textView11Name.text = fullname
////                    binding.textView11UserNamd.text = email
//                }
//
//                customEditDialog.show()
//                customEditDialog.dismiss()
//            }
//
//            button6Cancel.setOnClickListener {
//                var i = Intent(this.context, MainActivity::class.java)
//                startActivity(i)
//            }
//        }

        //get data from firebase
        userViewModel.getUserData().observe(viewLifecycleOwner) {

        }

        return binding.root
    }

    private fun getUserData() {
       // showProgressBar()
        val db = Firebase.firestore
        var encodedImage = ""

        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                usre = snapshot.getValue(EditUser::class.java)!!
                //set name and bio to fragment
                binding.tvFullName.setText(usre.firstName + " " + usre.lastName)
                binding.tvBio.setText(usre.bio)
                //set image in profile fragment
//                val localFill = File.createTempFile("tempImage","jpg")
//                val bitmap = BitmapFactory.decodeFile(localFill.absolutePath)
//                binding.profliImageFragment.setImageBitmap(bitmap)
                db.collection("users").document(auth.currentUser?.uid!!)
                    .get()
                    .addOnSuccessListener {
                        encodedImage = it.getString("avatar").toString()
                        var imageBitmap = ImageEncoding.decodeBase64(encodedImage)
                        binding.profliImageFragment.setImageBitmap(imageBitmap)
                    }
                getUserProfile()

            }

            override fun onCancelled(error: DatabaseError) {
             //   hideProgressBar()
                Toast.makeText(view?.context, "Failed to get User profile data", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getUserProfile() {
        storageReference= FirebaseStorage.getInstance().reference.child("UsersProfile/$uid.jpg")
        val localFill = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFill).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localFill.absolutePath)
            binding.profliImageFragment.setImageBitmap(bitmap)
         //   hideProgressBar()


        }.addOnFailureListener{

          //  hideProgressBar()
            Toast.makeText(view?.context, "Failed to retrieve image", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            // Use Uri object instead of File to avoid storage permissions
            binding.profliImageFragment.setImageURI(uri)
            imageView4EditProfile.setImageURI(uri)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}