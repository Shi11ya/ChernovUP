package com.example.z1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    private var likeCount = 1100
    private var commentCount = 10654
    private var shareCount = 1300000
    private var viewCount = 10000000
    private var isLiked = false

    private lateinit var likeButton: ImageButton
    private lateinit var likeCountTextView: TextView
    private lateinit var shareButton: ImageButton
    private lateinit var shareCountTextView: TextView
    private lateinit var viewCountTextView: TextView
    private lateinit var commentCountTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val mainLayout = findViewById<View>(R.id.main)
        if (mainLayout == null) {
            Log.e("MainActivity2", "mainLayout is null")
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        likeButton = findViewById(R.id.like_button)
        likeCountTextView = findViewById(R.id.like_count)
        commentCountTextView = findViewById(R.id.comment_count)
        shareButton = findViewById(R.id.share_button)
        shareCountTextView = findViewById(R.id.share_count)
        viewCountTextView = findViewById(R.id.view_count)

        setupListeners()
        updateUI()
    }

    private fun setupListeners() {
        likeButton.setOnClickListener {
            toggleLike()
        }

        shareButton.setOnClickListener {
            shareCount++
            updateUI()
        }
    }

    private fun toggleLike() {
        isLiked = !isLiked
        if (isLiked) {
            likeCount++
            likeButton.setImageResource(R.drawable.likered)
        } else {
            likeCount--
            likeButton.setImageResource(R.drawable.like)
        }
        updateUI()
    }

    private fun updateUI() {
        likeCountTextView.text = formatCount(likeCount)
        commentCountTextView.text = formatCount(commentCount)
        shareCountTextView.text = formatCount(shareCount)
        viewCountTextView.text = formatCount(viewCount)
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1_000_000 -> {
                // Формат для миллионов
                val millions = count / 1_000_000.0
                if (millions % 1 == 0.0) {
                    "${millions.toInt()}M" // Целые миллионы
                } else {
                    String.format("%.1fM", millions).replace(",", ".").trimEnd('0').trimEnd('.') // Десятичные миллионы
                }
            }
            count >= 10_000 -> {
                // Формат для тысяч от 10,000 и выше
                if (count % 1000 == 0) {
                    "${count / 1000}K" // Целые тысячи
                } else {
                    "${
                        (count / 1000).toInt()
                    }K" // Убираем сотни, показываем только десятичные
                }
            }
            count >= 1_100 -> {
                // Формат для значений от 1,100 до 9,999
                String.format("%.1fK", count / 1000.0).replace(",", ".") // Десятичные значения
            }
            count >= 1_000 -> {
                // Для 1000 показываем "1K"
                "1K"
            }
            else -> count.toString() // Для значений менее 1,000
        }
    }
}


