package com.example.finle_project.View.Regestar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finle_project.Model.EditUser
import com.example.finle_project.Model.UserChate
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var name = findViewById<EditText>(R.id.editTextTextFullName)
        var email = findViewById<EditText>(R.id.editTextTextEmail)
        var password = findViewById<EditText>(R.id.editTextTextPassword)
        var buttonRegister = findViewById<Button>(R.id.buttonDone)


        buttonRegister.setOnClickListener {
            firebaseAuth = Firebase.auth
            firebaseAuth.createUserWithEmailAndPassword(
                email.text.toString(),
                password.text.toString(),
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // for userChat
                    addUserToDatabase(name.text.toString(), email.text.toString(), firebaseAuth.currentUser?.uid!!)
                    addUserProfileToDatabase(name.text.toString(),email.text.toString(),"",firebaseAuth.currentUser?.uid!!)

                    val user = hashMapOf(
                        "fullname" to name.text.toString(),
                        "email" to email.text.toString(),
                        "avatar" to ""

                        )




                    val db = Firebase.firestore
                    db.collection("users")
                        .document(firebaseAuth.currentUser?.uid.toString())
                        .set(user as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Log.e("Tu", "FirebaseError: ${it.message}")
                        }

                    Log.d("Tu", firebaseAuth.currentUser?.uid.toString())
                    val intent = Intent(this, MainActivity::class.java)

                    startActivity(intent)
                } else {
                    println("Error")
                }

            }.addOnFailureListener {
                println(it.message)
            }
        }


    }
// for chat
    private fun addUserToDatabase(name: String, email: String, uid: String) {

        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("users").child(uid).setValue(UserChate(name, email, uid))

    }
    private fun addUserProfileToDatabase(name: String, email: String, bio: String,uid: String) {

        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("UsersProfile").child(uid).setValue(EditUser(name, email, bio))

    }
}

