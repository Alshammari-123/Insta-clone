package com.example.finle_project.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finle_project.model.FbUser
import com.example.finle_project.repository.UserRepository

class UserViewModel :ViewModel() {

    val userRepost= UserRepository()
    fun getUserData():LiveData<FbUser>{
        return userRepost.getUserData()
    }


}