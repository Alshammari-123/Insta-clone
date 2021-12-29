package com.example.finle_project.Model

import java.io.Serializable

data class MyPost(
    var caption: String,
    val id: String?,
    val imageUrl: String,
    var likes: Int,
    val userId: String
): Serializable