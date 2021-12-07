package com.example.finle_project.View.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.finle_project.R
import com.example.finle_project.View.Regestar.Registration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
var button=findViewById<Button>(R.id.button)
        button.setOnClickListener {
            var i=Intent(this,Registration::class.java)
            startActivity(i)
        }
    }
}