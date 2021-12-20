package com.example.finle_project.Model

import java.io.Serializable

data class MyPost(
    val caption: String,
    val id: String?,
    val imageUrl: String,
    val likes: Int,
    val userId: String
): Serializable