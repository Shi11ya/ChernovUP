package com.example.z1.repository

import com.example.z1.model.Post

interface PostRepository {
    fun getAllPosts(): List<Post>
    fun getPostById(id: Int): Post?
    fun updatePost(post: Post)
    fun deletePost(id: Int)
    fun addPost(post: Post)
} 