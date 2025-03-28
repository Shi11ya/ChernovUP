package com.example.z1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    var text: String,
    val publicationDate: String,
    var likeCount: Int,
    var commentCount: Int,
    var shareCount: Int,
    var viewCount: Int,
    var isLiked: Boolean
) : Parcelable 