package com.example.finle_project.Network

import com.example.finle_project.Model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserServic {

    @GET ("users")
    fun getUserSearchById(@Query("id")id:String): Call<List<User>>

}