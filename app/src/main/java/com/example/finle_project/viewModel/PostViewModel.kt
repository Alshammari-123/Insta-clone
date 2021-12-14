package com.example.finle_project.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finle_project.Model.MyPost
import com.example.finle_project.Repository.PostRepository

class PostViewModel : ViewModel() {

    val postRep = PostRepository()
    fun getAllPosts(): LiveData<List<MyPost>> {

        val mLiveData = MutableLiveData<List<MyPost>>()
        postRep.getAllPost().observeForever {
            mLiveData.postValue(it)
        }
        return mLiveData
    }


    fun createPost(post: MyPost): LiveData<MyPost> {
        val mLiveData = MutableLiveData<MyPost>()
        postRep.createPost(post).observeForever {
            mLiveData.postValue(it)
        }

        return mLiveData

    }
}