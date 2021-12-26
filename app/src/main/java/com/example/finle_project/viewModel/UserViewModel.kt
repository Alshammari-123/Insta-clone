package com.example.finle_project.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finle_project.Model.FbUser
import com.example.finle_project.Repository.UserRepository

class UserViewModel :ViewModel() {

    val userRepost= UserRepository()
    fun getUserData():LiveData<FbUser>{
        return userRepost.getUserData()
    }


}