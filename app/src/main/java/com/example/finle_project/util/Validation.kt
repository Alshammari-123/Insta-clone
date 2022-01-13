package com.example.finle_project.util

import android.provider.ContactsContract
import java.util.regex.Pattern

class Validation {
    private val REGEX_PASSWORD = "^(?=.*[0-9])"+
            "(?=.*[a-z])"+
            "(?=.*[A-Z])"+
            "(?=\\S+$)"+
            ".{8,}$"
    private val REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"

    fun emailIsValid(email:String):Boolean{
        val pattern = Pattern.compile(REGEX_EMAIL)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
    fun passwordIsValid(password:String):Boolean{
        val pattern = Pattern.compile(REGEX_PASSWORD)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}