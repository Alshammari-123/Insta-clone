package com.example.finle_project.View.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.finle_project.R
import com.example.finle_project.View.home.NewPost.NewPostActivity
import com.example.finle_project.View.home.chat_fragment.Chat_Fragment
import com.example.finle_project.View.home.profile_fragment.ProfileFragment
import com.example.finle_project.View.home.search_fragment.SearchFragment
import com.example.finle_project.View.home.home_fragment.HomeFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myTabLayout = findViewById<TabLayout>(R.id.myTabLayout)
        var myToolbar = findViewById<Toolbar>(R.id.myToolbar)

        myTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.home_fragment_container_view, HomeFragment())
                        .commit()
                    1 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.home_fragment_container_view, Chat_Fragment())
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
           when(it.itemId){
               R.id.add -> {
                   val intent = Intent(this,NewPostActivity::class.java)
                   startActivity(intent)
               }

           }
           true


       }
    }


}
