package com.example.finle_project.View.home.profile_fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finle_project.Model.MyPost
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity.MainActivity
import com.example.finle_project.databinding.FragmentBlankProfileBinding
import com.example.finle_project.util.ImageEncoding
import com.example.finle_project.viewModel.PostViewModel
import com.example.finle_project.viewModel.UserViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentBlankProfileBinding
    private lateinit var imageView4EditProfile: ImageView
    val vm: PostViewModel by viewModels()
    val viewModel: PostViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentBlankProfileBinding.inflate(layoutInflater, container, false)
        //profileRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.ProfileRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        FirebaseAuth.getInstance().currentUser?.let { user ->
            viewModel.getMyPost(user.uid).observe(viewLifecycleOwner, { postList ->
                Log.d(TAG, "onCreateView: $postList")
                binding.profileProgressBar
                binding.profileProgressBar.visibility = View.GONE
                binding.ProfileRecyclerView.adapter =
                    AdapterProfile(postList as MutableList<MyPost>)
            })
        }

        binding.buttonEditProfile.setOnClickListener {
            val customEditDialog = AlertDialog.Builder(context).create()
            var v = LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null, false)
            var db = Firebase.firestore
            var auth = Firebase.auth
            customEditDialog.setView(v)
            customEditDialog.show()

            imageView4EditProfile = v.findViewById(R.id.imageView4EditProfile)
            var editTextTextPersonName = v.findViewById<EditText>(R.id.editTextTextPersonName)
            var editTextUsername = v.findViewById<EditText>(R.id.editTextUsername)
            var button6Cancel = v.findViewById<Button>(R.id.button6Cancel)
            var button7Done = v.findViewById<Button>(R.id.button7Done)
            var textView9ChangePhotoProfilEdit =
                v.findViewById<TextView>(R.id.textView9ChangePhotoProfilEdit)

            textView9ChangePhotoProfilEdit.setOnClickListener {
                //binding.imageViewNewPost.setImageBitmap(decodePicFromApi(currentPost.imageUrl))
                // onActivityResult(0, 0, activity)
                ImagePicker.with(this)
                    .crop()
                    .compress(50)
                    .maxResultSize(1080, 1080)
                    .start()
            }

            //update data for profile
            button7Done.setOnClickListener {
                val fullname = editTextTextPersonName.text.toString()
                val email = editTextUsername.text.toString()

                Toast.makeText(
                    this.context,
                    auth.currentUser?.uid.toString(),
                    Toast.LENGTH_SHORT
                )
                    .show()
                db.collection("users").document(auth.currentUser?.uid.toString()).update(
                    mapOf(
                        "FullName" to fullname,
                        "email" to email
                    )

                ).addOnSuccessListener {
                    Toast.makeText(
                        activity,
                        "Fullname updated to $fullname",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.textView11Name.text = fullname
                    binding.textView11UserNamd.text = email
                }

                customEditDialog.show()
                customEditDialog.dismiss()
            }

            button6Cancel.setOnClickListener {
                var i = Intent(this.context, MainActivity::class.java)
                startActivity(i)
            }
        }

        //get data from firebase
        userViewModel.getUserData().observe(viewLifecycleOwner) {

        }

        return binding.root
    }

    fun setImageInStorage(imgUri: Uri): LiveData<String> {
        var dbStorage = Firebase.storage
        val filename = UUID.randomUUID().toString() + ".jpg"
        val liveDataImage = MutableLiveData<String>()
        val ref = dbStorage.reference.child(Firebase.auth.uid.toString()).child(filename)
        val uploadTask = ref.putFile(imgUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                Log.d(ContentValues.TAG, "Not able to upload image")
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d(ContentValues.TAG, downloadUri.toString())
                liveDataImage.postValue(downloadUri.toString())
            }
        }.addOnFailureListener {
            Log.d(ContentValues.TAG, "Not able to upload image")
        }
        return liveDataImage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            // Use Uri object instead of File to avoid storage permissions
            binding.imageViewProfile.setImageURI(uri)
            imageView4EditProfile.setImageURI(uri)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun codApi(uri: Uri) {
        val encodedImage = ImageEncoding.encodeBase64(uri)
    }

}