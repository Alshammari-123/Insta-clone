package com.example.finle_project.model

import java.io.Serializable

data class MyPost(
    var caption: String,
    val id: String?,
    val imageUrl: String,
    var likes: Int,
    val userId: String,
    val userLocation: String? = null
): Serializable