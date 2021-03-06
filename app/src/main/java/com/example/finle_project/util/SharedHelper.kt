package com.example.finle_project.util

import android.content.Context

class SharedHelper {

    companion object {
        fun saveUserId(context: Context, uid: String): Unit {
            var pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            pref.edit()
                .putString("id,", uid).clear()
        }


        fun saveUserLocation(context: Context, latLng: String) {
            val pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            pref
                .edit()
                .putString("latLng", latLng)
                .apply()
        }

        fun getUserLocation(context: Context): String? {
            val pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

            return pref.getString("latLng", "0.0,0.0")
        }

    }


}