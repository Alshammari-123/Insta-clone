package com.example.finle_project.View.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.finle_project.R
import com.example.finle_project.View.home.NewPost.NewPostActivity
import com.example.finle_project.View.home.Setting.SettingActivity
import com.example.finle_project.View.home.chat_fragment.ChatFragment
import com.example.finle_project.View.home.home_fragment.HomeFragment
import com.example.finle_project.View.home.profile_fragment.ProfileFragment
import com.example.finle_project.View.home.search_fragment.SearchFragment
import com.example.finle_project.View.login.Login
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myTabLayout = findViewById<TabLayout>(R.id.myTabLayout)
        var myToolbar = findViewById<Toolbar>(R.id.myToolbar)

        setSupportActionBar(myToolbar)

        myTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.home_fragment_container_view, HomeFragment())
                        .commit()
                    1 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.home_fragment_container_view, ChatFragment())
                        .commit()
                    2 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.home_fragment_container_view, SearchFragment())
                        .commit()
                    3 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.home_fragment_container_view, ProfileFragment())
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        })


        myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    val intent = Intent(this, NewPostActivity::class.java)
                    startActivity(intent)
                }

                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }
//                R.id.language -> {
//                    setLocale("ar")
//                    finish()
//                }
                R.id.setting ->{
                    var i = Intent(this,SettingActivity::class.java)
                    startActivity(i)

                }
            }
            true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu)
        return true
    }

}
