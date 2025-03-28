package com.example.z1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.z1.R
import com.example.z1.model.Post

class PostDetailFragment : Fragment() {
    private var post: Post? = null
    private var isTextExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            post = it.getParcelable("post")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        post?.let { currentPost ->
            setupViews(view, currentPost)
            setupClickListeners(view, currentPost)
        }
    }

    private fun setupViews(view: View, post: Post) {
        // Инициализация всех view элементов
        val text = view.findViewById<TextView>(R.id.post_description)
        val showMoreText = view.findViewById<TextView>(R.id.show_more_text)
        val likeButton = view.findViewById<ImageButton>(R.id.like_button)
        val likeCountTextView = view.findViewById<TextView>(R.id.like_count)
        val commentCountTextView = view.findViewById<TextView>(R.id.comment_count)
        val shareButton = view.findViewById<ImageButton>(R.id.share_button)
        val shareCountTextView = view.findViewById<TextView>(R.id.share_count)
        val viewCountTextView = view.findViewById<TextView>(R.id.view_count)
        val moreOptionsButton = view.findViewById<ImageButton>(R.id.more_options)

        // Установка начальных значений
        text.text = if (isTextExpanded) post.text else post.text.split("\n")[0]
        likeCountTextView.text = formatCount(post.likeCount)
        commentCountTextView.text = formatCount(post.commentCount)
        shareCountTextView.text = formatCount(post.shareCount)
        viewCountTextView.text = formatCount(post.viewCount)
        likeButton.setImageResource(if (post.isLiked) R.drawable.likered else R.drawable.like)
    }

    private fun setupClickListeners(view: View, post: Post) {
        val text = view.findViewById<TextView>(R.id.post_description)
        val showMoreText = view.findViewById<TextView>(R.id.show_more_text)
        val likeButton = view.findViewById<ImageButton>(R.id.like_button)
        val shareButton = view.findViewById<ImageButton>(R.id.share_button)
        val moreOptionsButton = view.findViewById<ImageButton>(R.id.more_options)

        // Обработчик для разворачивания/сворачивания текста
        showMoreText.setOnClickListener {
            toggleTextExpansion(text, showMoreText, post)
        }

        // Обработчик для лайка
        likeButton.setOnClickListener {
            toggleLike(post)
        }

        // Обработчик для репоста
        shareButton.setOnClickListener {
            updateShareCount(post)
        }

        // Обработчик для кнопки дополнительных опций
        moreOptionsButton.setOnClickListener {
            showOptionsDialog(post)
        }
    }

    private fun toggleTextExpansion(text: TextView, showMoreText: TextView, post: Post) {
        if (!isTextExpanded) {
            text.text = post.text
            showMoreText.text = "Скрыть"
            showMoreText.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
            isTextExpanded = true
        } else {
            text.text = post.text.split("\n")[0]
            showMoreText.text = "Показать ещё"
            showMoreText.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
            isTextExpanded = false
        }
    }

    private fun toggleLike(post: Post) {
        post.isLiked = !post.isLiked
        if (post.isLiked) {
            post.likeCount++
        } else {
            post.likeCount--
        }
        updateUI()
    }

    private fun updateShareCount(post: Post) {
        post.shareCount++
        updateUI()
    }

    private fun updateUI() {
        view?.let { view ->
            val likeCountTextView = view.findViewById<TextView>(R.id.like_count)
            val shareCountTextView = view.findViewById<TextView>(R.id.share_count)
            val likeButton = view.findViewById<ImageButton>(R.id.like_button)

            post?.let { currentPost ->
                likeCountTextView.text = formatCount(currentPost.likeCount)
                shareCountTextView.text = formatCount(currentPost.shareCount)
                likeButton.setImageResource(if (currentPost.isLiked) R.drawable.likered else R.drawable.like)
            }
        }
    }

    private fun showOptionsDialog(post: Post) {
        val options = arrayOf("Редактировать", "Удалить")
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Выберите действие")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditDialog(post)
                    1 -> showDeleteConfirmation(post)
                }
            }
            .create()
        
        dialog.show()
    }

    private fun showDeleteConfirmation(post: Post) {
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение")
            .setMessage("Вы уверены, что хотите удалить этот пост?")
            .setPositiveButton("Да") { _, _ ->
                deletePost(post)
            }
            .setNegativeButton("Нет", null)
            .create()
        
        dialog.show()
    }

    private fun deletePost(post: Post) {
        // Здесь будет логика удаления поста через репозиторий
        Toast.makeText(context, "Пост удален", Toast.LENGTH_SHORT).show()
        requireActivity().onBackPressed()
    }

    private fun showEditDialog(post: Post) {
        val currentText = if (isTextExpanded) post.text else post.text.split("\n")[0]
        val editText = android.widget.EditText(requireContext())
        editText.setText(currentText)
        editText.setLines(8)

        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Редактировать пост")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newText = editText.text.toString()
                post.text = newText
                updateUI()
                Toast.makeText(context, "Пост отредактирован", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .create()
        
        dialog.show()
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1_000_000 -> {
                val millions = count / 1_000_000.0
                if (millions % 1 == 0.0) {
                    "${millions.toInt()}M"
                } else {
                    String.format("%.1fM", millions).replace(",", ".").trimEnd('0').trimEnd('.')
                }
            }
            count >= 10_000 -> {
                if (count % 1000 == 0) {
                    "${count / 1000}K"
                } else {
                    "${(count / 1000).toInt()}K"
                }
            }
            count >= 1_100 -> {
                String.format("%.1fK", count / 1000.0).replace(",", ".")
            }
            count >= 1_000 -> {
                "1K"
            }
            else -> count.toString()
        }
    }

    companion object {
        fun newInstance(post: Post) = PostDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("post", post)
            }
        }
    }
} 