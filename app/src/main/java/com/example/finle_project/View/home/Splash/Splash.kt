package com.example.finle_project.View.home.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity
import com.example.finle_project.View.login.Login

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var logo= findViewById<LottieAnimationView>(R.id.logo)
        logo.setAnimation("logo.json")
        logo.playAnimation()
        logo.loop(true)

        Handler().postDelayed({

            var intent= Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        },1000)


    }
}