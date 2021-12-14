package com.example.finle_project.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finle_project.Model.MyPost
import com.example.finle_project.Network.API
import com.example.finle_project.Network.PostServic
import retrofit2.Call
import retrofit2.Response

class PostRepository {
    val postServic = API.getInstance().create(PostServic::class.java)

    fun getAllPost(): LiveData<List<MyPost>> {
        val mLiveData = MutableLiveData<List<MyPost>>()

        postServic.getAllPost().enqueue(object : retrofit2.Callback<MutableList<MyPost>> {
            override fun onResponse(
                call: Call<MutableList<MyPost>>,
                response: Response<MutableList<MyPost>>
            ) {
                mLiveData.postValue(response.body())
                Log.d("LIST_IS", "hello ${response.body()}")
            }

            override fun onFailure(call: Call<MutableList<MyPost>>, t: Throwable) {
                Log.d("FAILED_REPO", "${t.message}")
            }
        })
        return mLiveData
    }

    fun createPost(post: MyPost): LiveData<MyPost> {
        val mLiveData = MutableLiveData<MyPost>()

        postServic.createPost(post).enqueue(object : retrofit2.Callback<MyPost> {
            override fun onResponse(call: Call<MyPost>, response: Response<MyPost>) {
                Log.d("PICTURE_ENCODED", "response: ${response.body()}")
                mLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<MyPost>, t: Throwable) {
                Log.d("PICTURE_ENCODED", "hello ${t.message}")
            }

        })
        return mLiveData
    }
}