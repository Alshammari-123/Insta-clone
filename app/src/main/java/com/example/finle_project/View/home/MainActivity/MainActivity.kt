package com.example.finle_project.View.home.MainActivity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.finle_project.R
import com.example.finle_project.View.home.EditProfile.EditeProfileActivity
import com.example.finle_project.View.home.EditProfile.UserProfileActivity
import com.example.finle_project.View.home.NewPost.NewPostActivity
import com.example.finle_project.View.home.Setting.SettingActivity
import com.example.finle_project.View.home.Vido.AddVideosActivity
import com.example.finle_project.View.home.Vido.VideosActivity
import com.example.finle_project.View.home.chat_fragment.ChatFragment
import com.example.finle_project.View.home.home_fragment.HomeFragment
import com.example.finle_project.View.home.profile_fragment.ProfileFragment
import com.example.finle_project.View.home.search_fragment.SearchFragment
import com.example.finle_project.View.login.Login
import com.example.finle_project.nutel.SharedHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()


        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("FirebaseMessagingToken", "onCreate: $it")
        }

        var myTabLayout = findViewById<TabLayout>(R.id.myTabLayout)
        var myToolbar = findViewById<Toolbar>(R.id.myToolbar)
        var floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            var i = Intent(this,VideosActivity::class.java)
            startActivity(i)
        }

        setSupportActionBar(myToolbar)

        myTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fragment_container_view, HomeFragment())
                            .commit()

                        myToolbar.title = "Home"
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fragment_container_view, ChatFragment())
                            .commit()

                        myToolbar.title = "Chat"
                    }
                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fragment_container_view, SearchFragment())
                            .commit()

                        myToolbar.title = "Search"
                    }
                    3 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fragment_container_view, ProfileFragment())
                            .commit()

                        myToolbar.title = "Profile"
                    }
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

                R.id.setting -> {
                    var i = Intent(this, SettingActivity::class.java)
                    startActivity(i)

                }
                R.id.Profile -> {
                    var i = Intent(this,EditeProfileActivity::class.java)
                    startActivity(i)
                }
                R.id.userProfile ->{
                    var i = Intent(this,UserProfileActivity::class.java)
                    startActivity(i)
                }
                R.id.communication -> {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:")
                    intent.putExtra(Intent.EXTRA_EMAIL,"ko2ki2@hotmail.com")
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Customer Service")
                    intent.putExtra(Intent.EXTRA_TEXT,"")
                    startActivity(intent)
                }
            }
            true
        }
    }

    //for Location
    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED

        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }

        task.addOnSuccessListener {
            if (it != null) {
                SharedHelper.saveUserLocation(this, "${it.latitude},${it.longitude}")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu)
        return true
    }

}
