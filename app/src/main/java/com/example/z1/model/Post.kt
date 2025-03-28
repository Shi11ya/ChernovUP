package com.example.z1.model

data class Post(
    val id: Int,
    var text: String,
    var publicationDate: String,
    var likeCount: Int,
    var commentCount: Int,
    var shareCount: Int,
    var viewCount: Int,
    var isLiked: Boolean
) 