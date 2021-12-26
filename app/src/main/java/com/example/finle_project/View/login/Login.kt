package com.example.finle_project.View.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finle_project.R
import com.example.finle_project.View.Regestar.Registration
import com.example.finle_project.View.home.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val mAuthListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
  // if the user login befor you can go with out login agane
//        FirebaseAuth.getInstance().currentUser?.let {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//       }


        var emailEditText = findViewById<EditText>(R.id.emailEditText)
        var editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        var buttonLogIn = findViewById<Button>(R.id.buttonLogIn)
        var textButtonSignUp = findViewById<Button>(R.id.textButtonSignUp)

        buttonLogIn.setOnClickListener {

            Toast.makeText(this, "signing in ", Toast.LENGTH_SHORT).show()
            Log.d("sgin", "hello")
            auth = Firebase.auth

            if (emailEditText.text.toString().isNotEmpty()
                && editTextPassword.text.toString().isNotEmpty()
            ) {
                auth
                    .signInWithEmailAndPassword(
                        emailEditText.text.toString(), editTextPassword.text.toString()

                    )
                    .addOnSuccessListener {
                        Toast.makeText(this, "is success", Toast.LENGTH_SHORT).show()
                        var user = auth.currentUser
                        var i = Intent(this, MainActivity::class.java)

                        startActivity(i)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        it.message?.let { it1 -> Log.d("Turki", it1) }
                    }
            }

//            var SharedPreferences = SharedPreferences()

        }
        textButtonSignUp.setOnClickListener {
            var i = Intent(this, Registration::class.java)
            startActivity(i)
        }


    }


}
