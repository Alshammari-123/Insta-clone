package com.example.finle_project.View.Regestar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class Registration : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var editTextTextFullName=findViewById<EditText>(R.id.editTextTextFullName)
        var editTextTextEmail=findViewById<EditText>(R.id.editTextTextEmail)
        var editTextTextPassword=findViewById<EditText>(R.id.editTextTextPassword)
        var buttonRegister = findViewById<Button>(R.id.buttonRegister)


        buttonRegister.setOnClickListener {
            var auth=Firebase.auth
            auth.createUserWithEmailAndPassword(
                editTextTextEmail.text.toString(),
                editTextTextPassword.text.toString()

            ) .addOnCompleteListener { task->
                if (task.isSuccessful){
                    val user = hashMapOf(
                        "full name" to editTextTextFullName.text.toString(),
                        "email" to editTextTextEmail.text.toString(),
                        "password" to editTextTextPassword.text.toString(),
                        "user id" to true

                    )
                    var db=Firebase.firestore
                    db.collection("users").document(auth.currentUser?.uid.toString())
                        .set(user)
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)



                } else{
                    println("Error")
                }

            } .addOnFailureListener {
                println(it.message)
            }
        }


    }
}