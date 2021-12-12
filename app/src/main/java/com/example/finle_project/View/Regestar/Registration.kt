package com.example.finle_project.View.Regestar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var editTextTextFullName = findViewById<EditText>(R.id.editTextTextFullName)
        var editTextTextEmail = findViewById<EditText>(R.id.editTextTextEmail)
        var editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        var buttonRegister = findViewById<Button>(R.id.buttonDone)


        buttonRegister.setOnClickListener {
            firebaseAuth = Firebase.auth
            firebaseAuth.createUserWithEmailAndPassword(
                editTextTextEmail.text.toString(),
                editTextTextPassword.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = hashMapOf(
                        "fullname" to editTextTextFullName.text.toString(),
                        "email" to editTextTextEmail.text.toString()
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
}