package com.example.finle_project.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finle_project.Model.MyPost
import com.example.finle_project.Network.API
import com.example.finle_project.Network.PostServic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PostRepository"

class PostRepository {
    val postServic = API.getInstance().create(PostServic::class.java)

    fun getAllPost(): MutableLiveData<List<MyPost>> {
        val mLiveData = MutableLiveData<List<MyPost>>()

        postServic.getAllPost().enqueue(object : Callback<MutableList<MyPost>> {
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

        postServic.createPost(post).enqueue(object : Callback<MyPost> {
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

    fun getMyPst(id: String): MutableLiveData<List<MyPost>> {

        val mLiveData = MutableLiveData<List<MyPost>>()
        postServic.getMyPost(id).enqueue(object : Callback<List<MyPost>> {
            override fun onResponse(call: Call<List<MyPost>>, response: Response<List<MyPost>>) {
                mLiveData.postValue(response.body())
                Log.d("LIST_IS", "hello ${response.body()}")
                Log.d("getMyPostt", "onResponse: ${response.raw()}")
            }

            override fun onFailure(call: Call<List<MyPost>>, t: Throwable) {
                Log.d("FAILED_REPO", "${t.message}")
            }

        })

        return mLiveData
    }

    fun getTheUser(caption: String): MutableLiveData<List<MyPost>> {

        val mLiveData = MutableLiveData<List<MyPost>>()
        postServic.getTheUser(caption).enqueue(object : Callback<List<MyPost>> {
            override fun onResponse(call: Call<List<MyPost>>, response: Response<List<MyPost>>) {
                Log.d("PICTURE_ENCODED", "response: ${response.body()}")
                mLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<MyPost>>, t: Throwable) {
                Log.d("PICTURE_ENCODED", "hello ${t.message}")
            }

        })
        return mLiveData
    }

    fun deletePost(id: String): MutableLiveData<List<MyPost>> {
        val mLiveData = MutableLiveData<List<MyPost>>()
        postServic.deletePost(id).enqueue(object : Callback<List<MyPost>> {
            override fun onResponse(call: Call<List<MyPost>>, response: Response<List<MyPost>>) {
                Log.d("PICTURE_ENCODED", "response: ${response.body()}")
                Log.d(TAG, "onResponse: ${response.raw()}")
                mLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<MyPost>>, t: Throwable) {
                Log.d("PICTURE_ENCODED", "hello ${t.message}")
            }

        })
        return mLiveData
    }

    fun updatePost(post: MyPost): MutableLiveData<MyPost> {
        val mLiveData = MutableLiveData<MyPost>()
        postServic.updatePost(post.id!!, post).enqueue(object : Callback<MyPost> {
            override fun onResponse(call: Call<MyPost>, response: Response<MyPost>) {
                Log.d("PICTURE_ENCODED", "response: ${response.body()}")
                Log.d("POST_REPO", "Raw: ${response.raw()}")
                mLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<MyPost>, t: Throwable) {
                Log.d("PICTURE_ENCODED", "hello ${t.message}")
            }

        })
        return mLiveData
    }

}