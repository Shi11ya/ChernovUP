package com.example.z1.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.z1.model.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SQLitePostRepository(context: Context) : PostRepository {
    private val dbHelper = PostDbHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    init {
        // Проверяем, есть ли данные в базе
        val cursor = db.query(PostDbHelper.TABLE_POSTS, null, null, null, null, null, null)
        if (cursor.count == 0) {
            cursor.close()
            migrateFromJson(context)
        } else {
            cursor.close()
        }
    }

    private fun migrateFromJson(context: Context) {
        try {
            val jsonString = context.assets.open("posts.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, List<Post>>>() {}.type
            val jsonData = Gson().fromJson<Map<String, List<Post>>>(jsonString, type)
            val posts = jsonData["posts"] ?: emptyList()

            // Начинаем транзакцию
            db.beginTransaction()
            try {
                // Очищаем таблицу
                db.delete(PostDbHelper.TABLE_POSTS, null, null)

                // Вставляем данные
                posts.forEach { post ->
                    val values = ContentValues().apply {
                        put(PostDbHelper.COLUMN_ID, post.id)
                        put(PostDbHelper.COLUMN_TEXT, post.text)
                        put(PostDbHelper.COLUMN_PUBLICATION_DATE, post.publicationDate)
                        put(PostDbHelper.COLUMN_LIKE_COUNT, post.likeCount)
                        put(PostDbHelper.COLUMN_COMMENT_COUNT, post.commentCount)
                        put(PostDbHelper.COLUMN_SHARE_COUNT, post.shareCount)
                        put(PostDbHelper.COLUMN_VIEW_COUNT, post.viewCount)
                        put(PostDbHelper.COLUMN_IS_LIKED, if (post.isLiked) 1 else 0)
                    }
                    db.insert(PostDbHelper.TABLE_POSTS, null, values)
                }
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAllPosts(): List<Post> {
        val posts = mutableListOf<Post>()
        val cursor = db.query(
            PostDbHelper.TABLE_POSTS,
            null,
            null,
            null,
            null,
            null,
            "${PostDbHelper.COLUMN_ID} DESC"
        )

        with(cursor) {
            while (moveToNext()) {
                val post = Post(
                    id = getInt(getColumnIndexOrThrow(PostDbHelper.COLUMN_ID)),
                    text = getString(getColumnIndexOrThrow(PostDbHelper.COLUMN_TEXT)),
                    publicationDate = getString(getColumnIndexOrThrow(PostDbHelper.COLUMN_PUBLICATION_DATE)),
                    likeCount = getInt(getColumnIndexOrThrow(PostDbHelper.COLUMN_LIKE_COUNT)),
                    commentCount = getInt(getColumnIndexOrThrow(PostDbHelper.COLUMN_COMMENT_COUNT)),
                    shareCount = getInt(getColumnIndexOrThrow(PostDbHelper.COLUMN_SHARE_COUNT)),
                    viewCount = getInt(getColumnIndexOrThrow(PostDbHelper.COLUMN_VIEW_COUNT)),
                    isLiked = getInt(getColumnIndexOrThrow(PostDbHelper.COLUMN_IS_LIKED)) == 1
                )
                posts.add(post)
            }
        }
        cursor.close()
        return posts
    }

    override fun getPostById(id: Int): Post? {
        val cursor = db.query(
            PostDbHelper.TABLE_POSTS,
            null,
            "${PostDbHelper.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val post = Post(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_ID)),
                text = cursor.getString(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_TEXT)),
                publicationDate = cursor.getString(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_PUBLICATION_DATE)),
                likeCount = cursor.getInt(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_LIKE_COUNT)),
                commentCount = cursor.getInt(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_COMMENT_COUNT)),
                shareCount = cursor.getInt(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_SHARE_COUNT)),
                viewCount = cursor.getInt(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_VIEW_COUNT)),
                isLiked = cursor.getInt(cursor.getColumnIndexOrThrow(PostDbHelper.COLUMN_IS_LIKED)) == 1
            )
            cursor.close()
            post
        } else {
            cursor.close()
            null
        }
    }

    override fun updatePost(post: Post) {
        val values = ContentValues().apply {
            put(PostDbHelper.COLUMN_TEXT, post.text)
            put(PostDbHelper.COLUMN_PUBLICATION_DATE, post.publicationDate)
            put(PostDbHelper.COLUMN_LIKE_COUNT, post.likeCount)
            put(PostDbHelper.COLUMN_COMMENT_COUNT, post.commentCount)
            put(PostDbHelper.COLUMN_SHARE_COUNT, post.shareCount)
            put(PostDbHelper.COLUMN_VIEW_COUNT, post.viewCount)
            put(PostDbHelper.COLUMN_IS_LIKED, if (post.isLiked) 1 else 0)
        }

        db.update(
            PostDbHelper.TABLE_POSTS,
            values,
            "${PostDbHelper.COLUMN_ID} = ?",
            arrayOf(post.id.toString())
        )
    }

    override fun deletePost(id: Int) {
        db.delete(
            PostDbHelper.TABLE_POSTS,
            "${PostDbHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun addPost(post: Post) {
        val values = ContentValues().apply {
            put(PostDbHelper.COLUMN_ID, post.id)
            put(PostDbHelper.COLUMN_TEXT, post.text)
            put(PostDbHelper.COLUMN_PUBLICATION_DATE, post.publicationDate)
            put(PostDbHelper.COLUMN_LIKE_COUNT, post.likeCount)
            put(PostDbHelper.COLUMN_COMMENT_COUNT, post.commentCount)
            put(PostDbHelper.COLUMN_SHARE_COUNT, post.shareCount)
            put(PostDbHelper.COLUMN_VIEW_COUNT, post.viewCount)
            put(PostDbHelper.COLUMN_IS_LIKED, if (post.isLiked) 1 else 0)
        }

        db.insert(PostDbHelper.TABLE_POSTS, null, values)
    }

    private class PostDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            const val DATABASE_NAME = "posts.db"
            const val DATABASE_VERSION = 1
            const val TABLE_POSTS = "posts"
            const val COLUMN_ID = "id"
            const val COLUMN_TEXT = "text"
            const val COLUMN_PUBLICATION_DATE = "publication_date"
            const val COLUMN_LIKE_COUNT = "like_count"
            const val COLUMN_COMMENT_COUNT = "comment_count"
            const val COLUMN_SHARE_COUNT = "share_count"
            const val COLUMN_VIEW_COUNT = "view_count"
            const val COLUMN_IS_LIKED = "is_liked"
        }

        override fun onCreate(db: SQLiteDatabase) {
            val createTable = """
                CREATE TABLE $TABLE_POSTS (
                    $COLUMN_ID INTEGER PRIMARY KEY,
                    $COLUMN_TEXT TEXT NOT NULL,
                    $COLUMN_PUBLICATION_DATE TEXT NOT NULL,
                    $COLUMN_LIKE_COUNT INTEGER NOT NULL,
                    $COLUMN_COMMENT_COUNT INTEGER NOT NULL,
                    $COLUMN_SHARE_COUNT INTEGER NOT NULL,
                    $COLUMN_VIEW_COUNT INTEGER NOT NULL,
                    $COLUMN_IS_LIKED INTEGER NOT NULL
                )
            """.trimIndent()
            db.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
            onCreate(db)
        }
    }
} 