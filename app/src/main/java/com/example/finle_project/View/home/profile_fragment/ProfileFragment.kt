package com.example.finle_project.View.home.profile_fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.MyPost
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity
import com.example.finle_project.viewModel.PostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    val viewModel: PostViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_blank_profile, container, false)
        val progressBar = v.findViewById<ProgressBar>(R.id.profileProgressBar)
        var imageViewProfile = v.findViewById<ImageView>(R.id.imageViewProfile)
        var buttonEditProfile = v.findViewById<Button>(R.id.buttonEditProfile)
        var textView11UserNamd = v.findViewById<TextView>(R.id.textView11Name)
        var textView9Followirse = v.findViewById<TextView>(R.id.textView9Followirse)
        var textView10Folloing = v.findViewById<TextView>(R.id.textView10Folloing)

        var profileRecyclerView = v.findViewById<RecyclerView>(R.id.ProfileRecyclerView)
        //profileRecyclerView.layoutManager= LinearLayoutManager(context)
        profileRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        FirebaseAuth.getInstance().currentUser?.let { user ->
            viewModel.getMyPost(user.uid).observe(viewLifecycleOwner, { postList ->
                Log.d(TAG, "onCreateView: $postList")
                progressBar.visibility = View.GONE
                profileRecyclerView.adapter = AdapterProfile(postList as MutableList<MyPost>)
            })
        }


        buttonEditProfile.setOnClickListener {
            val customEditDialog = AlertDialog.Builder(context).create()
            var v = LayoutInflater.from(context).inflate(R.layout.dialog_edit_profile, null, false)
            var db = Firebase.firestore
            var auth = Firebase.auth
            customEditDialog.setView(v)
            customEditDialog.show()

            val userNameTextView = v.findViewById<TextView>(R.id.textView11Name)
            var imageView4EditProfile = v.findViewById<ImageView>(R.id.imageView4EditProfile)
            var editTextTextPersonName = v.findViewById<EditText>(R.id.editTextTextPersonName)
            var editTextUsername = v.findViewById<EditText>(R.id.editTextUsername)
            var button6Cancel = v.findViewById<Button>(R.id.button6Cancel)
            var button7Done = v.findViewById<Button>(R.id.button7Done)



            button7Done.setOnClickListener {
                val fullname = editTextTextPersonName.text.toString()
                val email = editTextUsername.text.toString()

                Toast.makeText(this.context, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT)
                    .show()
                db.collection("users").document(auth.currentUser?.uid.toString()).set(
                    mapOf(
                        "FullName" to fullname,
                        "email" to email
                    )

                ).addOnSuccessListener {
                    Toast.makeText(activity, "Fullname updated to $fullname", Toast.LENGTH_SHORT)
                        .show()
                    userNameTextView.text = fullname
                }

                customEditDialog.show()
                customEditDialog.dismiss()


            }

            button6Cancel.setOnClickListener {
                var i = Intent(this.context, MainActivity::class.java)
                startActivity(i)


            }


        }





        return v


    }

}
