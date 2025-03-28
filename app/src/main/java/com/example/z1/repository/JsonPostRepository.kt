package com.example.z1.repository

import android.content.Context
import com.example.z1.model.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonPostRepository(private val context: Context) : PostRepository {
    private val gson = Gson()
    private val fileName = "posts.json"
    private val posts = mutableListOf<Post>()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, List<Post>>>() {}.type
            val jsonData = gson.fromJson<Map<String, List<Post>>>(jsonString, type)
            posts.clear()
            posts.addAll(jsonData["posts"] ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun savePosts() {
        try {
            val jsonString = gson.toJson(mapOf("posts" to posts))
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { 
                it.write(jsonString.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAllPosts(): List<Post> = posts

    override fun getPostById(id: Int): Post? = posts.find { it.id == id }

    override fun updatePost(post: Post) {
        val index = posts.indexOfFirst { it.id == post.id }
        if (index != -1) {
            posts[index] = post
            savePosts()
        }
    }

    override fun deletePost(id: Int) {
        posts.removeIf { it.id == id }
        savePosts()
    }

    override fun addPost(post: Post) {
        posts.add(post)
        savePosts()
    }
} 