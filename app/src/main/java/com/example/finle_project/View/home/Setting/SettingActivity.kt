package com.example.finle_project.View.home.Setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.finle_project.R
import com.example.finle_project.View.home.MainActivity
import java.util.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var switchDark = findViewById<Switch>(R.id.switchDark)
        var spinnerLangueg = findViewById<Spinner>(R.id.spinnerLangueg)

        spinnerLangueg.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item,
            arrayOf("chose language","ENGLISH", "العربية")
        )


        spinnerLangueg.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> setLocale("en")
                    2 -> setLocale("ar")

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        switchDark.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    fun setLocale(localeName: String?) {
        val locale = Locale(localeName)
        val res = resources
//        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(locale)
        res.updateConfiguration(conf, resources.displayMetrics)
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        startActivity(intent)
        finish()
    }
}