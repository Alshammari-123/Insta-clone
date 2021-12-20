package com.example.finle_project.nutel

import android.content.Context
import android.provider.ContactsContract

class sheardHelper {

    companion object{

        fun saveUserId(context: Context,uid:String):Unit{
            var pref = context.getSharedPreferences("MyPref",Context.MODE_PRIVATE)
            pref.edit()
                .putString("id,",uid).clear()
        }

        fun getUserId(context: Context):String{
            var perf=context.getSharedPreferences("MyPref",Context.MODE_PRIVATE)
            var id = perf.getString("id","null")
            return id!!
        }

    }



}