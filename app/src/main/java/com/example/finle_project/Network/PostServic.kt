package com.example.finle_project.Network

import com.example.finle_project.Model.MyPost
import retrofit2.Call
import retrofit2.http.*

interface PostServic {

    //get all posts

    @GET("posts")
    fun getAllPost(): Call<MutableList<MyPost>>

    //create new post
    @POST("posts")
    fun createPost(@Body post: MyPost): Call<MyPost>

    // get user's posts
    @GET("posts")
    fun getMyPost(@Query("userId") id: String): Call<List<MyPost>>

    // search user
    @GET("posts")
    fun getTheUser(@Query(" caption") caption: String): Call<List<MyPost>>

    // delete post
    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: String): Call<List<MyPost>>

    // to update
    @PUT("posts/{id}")
    fun updatePost(@Path("id") id: String, @Body post: MyPost): Call<MyPost>


}