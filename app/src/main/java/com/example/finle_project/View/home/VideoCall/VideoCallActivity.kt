package com.example.finle_project.View.home.VideoCall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import com.example.finle_project.R
import com.github.dhaval2404.imagepicker.util.PermissionUtil.isPermissionGranted
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class VideoCallActivity : AppCompatActivity() {
val permission = arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO)
    val requestcode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)

        var loginBtn = findViewById<Button>(R.id.loginBtn)
        var usernameEdit = findViewById<EditText>(R.id.usernameEdit)

        if (! isPermissionGranted()){
            askPermiddion()
        }
        Firebase.initialize(this)
        loginBtn.setOnClickListener {
           val username =  usernameEdit.text.toString()
            val intent = Intent(this,CallActivity::class.java)
            intent.putExtra("useername" , username)
            startActivity(intent)

        }

    }

    private fun askPermiddion() {

        ActivityCompat.requestPermissions(this,permission,requestcode)

    }


    private fun isPermissionGranted(): Boolean {
        permission.forEach {
            if (ActivityCompat.checkSelfPermission(this,it)!=PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }
    }

