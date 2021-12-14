package com.example.finle_project.Network

import com.example.finle_project.Model.MyPost
import com.google.firebase.firestore.auth.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostServic {

    @GET ("posts")
     fun getAllPost(): Call<MutableList<MyPost>>

    @POST("posts")
    fun createPost(@Body post:MyPost):Call<MyPost>

}