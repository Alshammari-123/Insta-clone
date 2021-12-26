package com.example.finle_project.Repository

import androidx.lifecycle.MutableLiveData
import com.example.finle_project.Model.FbUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepository {

    //
//    fun (email: String, password: String): Unit {
//
//
//
//    }
    fun getUserData(): MutableLiveData<FbUser> {
        val liveData = MutableLiveData<FbUser>()
        var db = Firebase.firestore
        var auth = Firebase.auth

        db.collection("users").document(auth.currentUser?.uid.toString())
            .addSnapshotListener { users, error ->
                if (users != null) {
                    val fullname = users["fullname"] as String
                    val username = users["username"] as String? ?: ""
                    val photo = users["photo"] as String? ?: ""

                    val user = FbUser(fullname, username, photo)

                    liveData.postValue(user)
                }
            }

        return liveData
    }

}