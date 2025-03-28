package com.example.z1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import android.app.AlertDialog
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.FrameLayout
import com.example.z1.fragments.PostDetailFragment
import com.example.z1.model.Post
import com.example.z1.repository.JsonPostRepository

/**
 * Активность профиля сообщества
 * Отображает информацию о сообществе и список постов в виде прокручиваемого списка
 */
class MainActivity3 : AppCompatActivity() {
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var repository: JsonPostRepository
    private lateinit var overlayBackground: View
    private lateinit var popupPostContainer: View
    private lateinit var closeButton: ImageButton
    private var isLoading = false
    private var currentPage = 1
    private val postsPerPage = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        repository = JsonPostRepository(this)

        // Инициализация views
        overlayBackground = findViewById(R.id.overlay_background)
        popupPostContainer = findViewById(R.id.popup_post_container)
        closeButton = findViewById(R.id.close_button)

        // Настройка кнопки возврата
        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }

        // Настройка кнопки закрытия всплывающего поста
        closeButton.setOnClickListener {
            hidePostPopup()
        }

        // Настройка клика по затемненному фону
        overlayBackground.setOnClickListener {
            hidePostPopup()
        }

        // Инициализация RecyclerView для отображения постов
        postsRecyclerView = findViewById(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        
        // Загружаем первые посты
        val initialPosts = repository.getAllPosts()
        postsAdapter = PostsAdapter(initialPosts, { post, isTextExpanded ->
            showPostPopup(post, isTextExpanded)
        }, repository, postsRecyclerView)
        postsRecyclerView.adapter = postsAdapter

        // Добавляем слушатель прокрутки для бесконечной ленты
        postsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 5
                    && firstVisibleItemPosition >= 0) {
                    loadMorePosts()
                    isLoading = true
                }
            }
        })

        // Настройка системных отступов
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadMorePosts() {
        // Имитируем загрузку с задержкой
        postsRecyclerView.postDelayed({
            // Получаем все существующие посты из репозитория
            val existingPosts = repository.getAllPosts()
            
            // Создаем новые посты, циклически используя существующие
            val newPosts = (1..postsPerPage).map { index ->
                val newId = (currentPage - 1) * postsPerPage + index
                // Используем остаток от деления для циклического выбора поста
                val templatePost = existingPosts[(newId - 1) % existingPosts.size]
                
                // Создаем новый пост на основе шаблона, но с новым ID
                Post(
                    id = newId,
                    text = templatePost.text,
                    publicationDate = templatePost.publicationDate,
                    likeCount = templatePost.likeCount,
                    commentCount = templatePost.commentCount,
                    shareCount = templatePost.shareCount,
                    viewCount = templatePost.viewCount,
                    isLiked = templatePost.isLiked
                )
            }
            
            currentPage++
            postsAdapter.addPosts(newPosts)
            isLoading = false
        }, 500) // Задержка 500мс для имитации загрузки
    }

    private fun showPostPopup(post: Post, isTextExpanded: Boolean) {
        // Показываем затемненный фон
        overlayBackground.visibility = View.VISIBLE
        
        // Показываем контейнер поста
        popupPostContainer.visibility = View.VISIBLE
        
        // Настраиваем контент поста
        val postContent = findViewById<View>(R.id.post_content)
        
        // Устанавливаем данные поста
        val avatarButton = postContent.findViewById<ImageButton>(R.id.avatar)
        avatarButton.setBackgroundResource(R.drawable.logo)

        val authorName = postContent.findViewById<TextView>(R.id.author_name)
        authorName.text = "Новости. Борисоглебский техникум промышленных и информационных технологий"

        val publicationDate = postContent.findViewById<TextView>(R.id.publication_date)
        publicationDate.text = post.publicationDate

        val postText = postContent.findViewById<TextView>(R.id.post_description)
        val showMoreText = postContent.findViewById<TextView>(R.id.show_more_text)
        
        // Устанавливаем состояние текста в соответствии с оригинальным постом
        if (isTextExpanded) {
            postText.text = post.text
            showMoreText.text = "Скрыть"
        } else {
            postText.text = post.text.split("\n")[0]
            showMoreText.text = "Показать ещё"
        }
        showMoreText.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
        
        // Добавляем обработчик для кнопки "Показать ещё/Скрыть"
        showMoreText.setOnClickListener {
            if (showMoreText.text == "Скрыть") {
                postText.text = post.text.split("\n")[0]
                showMoreText.text = "Показать ещё"
                showMoreText.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                // Обновляем состояние в адаптере
                postsAdapter.updateTextExpansion(post.id, false)
            } else {
                postText.text = post.text
                showMoreText.text = "Скрыть"
                showMoreText.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                // Обновляем состояние в адаптере
                postsAdapter.updateTextExpansion(post.id, true)
            }
        }

        val postImage = postContent.findViewById<ImageView>(R.id.post_image)
        val videoContainer = postContent.findViewById<FrameLayout>(R.id.video_container)
        val playButton = postContent.findViewById<ImageView>(R.id.play_button)

        // Используем остаток от деления для определения типа контента
        val contentType = (post.id - 1) % 3 + 1
        
        if (contentType == 3) {
            postImage.visibility = View.GONE
            videoContainer.visibility = View.VISIBLE
            videoContainer.setBackgroundResource(R.drawable.krasava3)
            playButton.setOnClickListener {
                val videoUrl = "https://www.youtube.com/watch?v=Kh_haVVhdjA"
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Не удалось открыть видео", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            postImage.visibility = View.VISIBLE
            videoContainer.visibility = View.GONE
            postImage.setImageResource(when (contentType) {
                1 -> R.drawable.krasavciki
                2 -> R.drawable.krasava2
                else -> R.drawable.krasavciki
            })
        }

        // Настраиваем счетчики
        val likeCountTextView = postContent.findViewById<TextView>(R.id.like_count)
        val commentCountTextView = postContent.findViewById<TextView>(R.id.comment_count)
        val shareCountTextView = postContent.findViewById<TextView>(R.id.share_count)
        val viewCountTextView = postContent.findViewById<TextView>(R.id.view_count)
        val likeButton = postContent.findViewById<ImageButton>(R.id.like_button)

        likeCountTextView.text = formatCount(post.likeCount)
        commentCountTextView.text = formatCount(post.commentCount)
        shareCountTextView.text = formatCount(post.shareCount)
        viewCountTextView.text = formatCount(post.viewCount)
        likeButton.setImageResource(if (post.isLiked) R.drawable.likered else R.drawable.like)

        // Настраиваем обработчики событий
        likeButton.setOnClickListener {
            post.isLiked = !post.isLiked
            if (post.isLiked) {
                post.likeCount++
                likeButton.setImageResource(R.drawable.likered)
            } else {
                post.likeCount--
                likeButton.setImageResource(R.drawable.like)
            }
            likeCountTextView.text = formatCount(post.likeCount)
            repository.updatePost(post)
            // Обновляем пост в адаптере
            postsAdapter.updatePost(post)
        }

        val shareButton = postContent.findViewById<ImageButton>(R.id.share_button)
        shareButton.setOnClickListener {
            post.shareCount++
            shareCountTextView.text = formatCount(post.shareCount)
            repository.updatePost(post)
            // Обновляем пост в адаптере
            postsAdapter.updatePost(post)
        }

        val moreOptionsButton = postContent.findViewById<ImageButton>(R.id.more_options)
        moreOptionsButton.setOnClickListener {
            showOptionsDialog(post)
        }
        
        // Настройка клика на весь пост
        postContent.setOnClickListener {
            showPostPopup(post, !isTextExpanded)
        }
    }

    private fun hidePostPopup() {
        // Скрываем затемненный фон
        overlayBackground.visibility = View.GONE
        
        // Скрываем контейнер поста
        popupPostContainer.visibility = View.GONE
    }

    private fun showOptionsDialog(post: Post) {
        val options = arrayOf("Редактировать", "Удалить")
        val dialog = AlertDialog.Builder(this)
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

    private fun showEditDialog(post: Post) {
        val editText = EditText(this)
        editText.setText(post.text)
        editText.setLines(8)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Редактировать пост")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newText = editText.text.toString()
                post.text = newText
                repository.updatePost(post)
                
                // Обновляем текст поста в всплывающем окне
                val postContent = findViewById<View>(R.id.post_content)
                val postText = postContent.findViewById<TextView>(R.id.post_description)
                val showMoreText = postContent.findViewById<TextView>(R.id.show_more_text)
                
                // Проверяем текущее состояние развернутости
                if (showMoreText.text == "Скрыть") {
                    postText.text = newText
                } else {
                    postText.text = newText.split("\n")[0]
                }
                
                // Обновляем пост в адаптере
                postsAdapter.updatePost(post)
                Toast.makeText(this, "Пост отредактирован", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .create()
        
        dialog.show()
    }

    private fun showDeleteConfirmation(post: Post) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Подтверждение")
            .setMessage("Вы уверены, что хотите удалить этот пост?")
            .setPositiveButton("Да") { _, _ ->
                repository.deletePost(post.id)
                // Удаляем пост из адаптера
                postsAdapter.deletePost(post.id)
                hidePostPopup()
                Toast.makeText(this, "Пост удален", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Нет", null)
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

    override fun onBackPressed() {
        if (popupPostContainer.visibility == View.VISIBLE) {
            hidePostPopup()
        } else {
            super.onBackPressed()
        }
    }
}

/**
 * Адаптер для отображения постов в RecyclerView
 * Реализует бесконечную прокрутку постов
 */
class PostsAdapter(
    private var posts: List<Post>,
    private val onPostClick: (Post, Boolean) -> Unit,
    private val repository: JsonPostRepository,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    fun addPosts(newPosts: List<Post>) {
        val oldSize = posts.size
        posts = posts + newPosts
        notifyItemRangeInserted(oldSize, newPosts.size)
    }

    fun updatePost(post: Post) {
        val index = posts.indexOfFirst { it.id == post.id }
        if (index != -1) {
            posts = posts.toMutableList().apply {
                this[index] = post
            }
            notifyItemChanged(index)
        }
    }

    fun deletePost(id: Int) {
        val index = posts.indexOfFirst { it.id == id }
        if (index != -1) {
            posts = posts.toMutableList().apply {
                this.removeAt(index)
            }
            notifyItemRemoved(index)
        }
    }

    fun updateTextExpansion(postId: Int, isExpanded: Boolean) {
        val index = posts.indexOfFirst { it.id == postId }
        if (index != -1) {
            // Находим ViewHolder для этого поста
            val viewHolder = (recyclerView.findViewHolderForAdapterPosition(index) as? PostViewHolder)
            viewHolder?.updateTextExpansion(isExpanded)
        }
    }

    /**
     * ViewHolder для отображения отдельного поста
     * Управляет отображением и взаимодействием с элементами поста
     */
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var isTextExpanded = false
        private var videoUrl: String? = null
        private var post: Post? = null

        // UI элементы поста
        private lateinit var likeButton: ImageButton
        private lateinit var likeCountTextView: TextView
        private lateinit var shareButton: ImageButton
        private lateinit var shareCountTextView: TextView
        private lateinit var viewCountTextView: TextView
        private lateinit var commentCountTextView: TextView
        private lateinit var text: TextView
        private lateinit var showMoreText: TextView
        private lateinit var moreOptionsButton: ImageButton
        private lateinit var videoContainer: FrameLayout
        private lateinit var playButton: ImageView

        /**
         * Привязка данных к UI элементам поста
         * post номер поста для отображения соответствующих данных
         */
        fun bind(post: Post) {
            this.post = post
            // Инициализация UI элементов
            initializeViews()

            // Установка данных поста
            setupPostData(post)

            // Настройка обработчиков событий
            setupEventListeners(post)

            // Настройка клика на весь пост
            itemView.setOnClickListener {
                onPostClick(post, isTextExpanded)
            }
        }

        // Инициализация UI элементов
        private fun initializeViews() {
            likeButton = itemView.findViewById(R.id.like_button)
            likeCountTextView = itemView.findViewById(R.id.like_count)
            commentCountTextView = itemView.findViewById(R.id.comment_count)
            shareButton = itemView.findViewById(R.id.share_button)
            shareCountTextView = itemView.findViewById(R.id.share_count)
            viewCountTextView = itemView.findViewById(R.id.view_count)
            text = itemView.findViewById(R.id.post_description)
            showMoreText = itemView.findViewById(R.id.show_more_text)
            moreOptionsButton = itemView.findViewById(R.id.more_options)
            videoContainer = itemView.findViewById(R.id.video_container)
            playButton = itemView.findViewById(R.id.play_button)
        }

        // Установка данных поста
        private fun setupPostData(post: Post) {
            val avatarButton = itemView.findViewById<ImageButton>(R.id.avatar)
            avatarButton.setBackgroundResource(R.drawable.logo)

            val authorName = itemView.findViewById<TextView>(R.id.author_name)
            authorName.text = "Новости. Борисоглебский техникум промышленных и информационных технологий"

            val publicationDate = itemView.findViewById<TextView>(R.id.publication_date)
            publicationDate.text = post.publicationDate

            val postImage = itemView.findViewById<ImageView>(R.id.post_image)
            // Используем остаток от деления для определения типа контента
            val contentType = (post.id - 1) % 3 + 1
            
            if (contentType == 3) {
                postImage.visibility = View.GONE
                videoContainer.visibility = View.VISIBLE
                videoContainer.setBackgroundResource(R.drawable.krasava3)
                videoUrl = "https://www.youtube.com/watch?v=Kh_haVVhdjA"
            } else {
                postImage.visibility = View.VISIBLE
                videoContainer.visibility = View.GONE
                postImage.setImageResource(when (contentType) {
                    1 -> R.drawable.krasavciki
                    2 -> R.drawable.krasava2
                    else -> R.drawable.krasavciki
                })
            }

            text.text = if (isTextExpanded) post.text else post.text.split("\n")[0]
            likeCountTextView.text = formatCount(post.likeCount)
            commentCountTextView.text = formatCount(post.commentCount)
            shareCountTextView.text = formatCount(post.shareCount)
            viewCountTextView.text = formatCount(post.viewCount)
            likeButton.setImageResource(if (post.isLiked) R.drawable.likered else R.drawable.like)
        }

        // Настройка обработчиков событий
        private fun setupEventListeners(post: Post) {
            // Предотвращаем всплытие события клика для кнопок
            val clickableViews = listOf(
                likeButton, shareButton, moreOptionsButton,
                videoContainer, playButton, itemView.findViewById<ImageButton>(R.id.avatar)
            )

            clickableViews.forEach { view ->
                view.setOnClickListener { 
                    when (view) {
                        likeButton -> toggleLike(post)
                        shareButton -> updateShareCount(post)
                        moreOptionsButton -> showOptionsDialog(post)
                        videoContainer, playButton -> videoUrl?.let { openVideo(it) }
                        else -> {}
                    }
                }
            }

            showMoreText.setOnClickListener {
                toggleTextExpansion(post)
            }
        }

        // Переключение состояния развернутости текста
        private fun toggleTextExpansion(post: Post) {
            isTextExpanded = !isTextExpanded
            if (isTextExpanded) {
                text.text = post.text
                showMoreText.text = "Скрыть"
                showMoreText.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_dark))
            } else {
                text.text = post.text.split("\n")[0]
                showMoreText.text = "Показать ещё"
                showMoreText.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_dark))
            }
        }

        // Переключение состояния лайка
        private fun toggleLike(post: Post) {
            post.isLiked = !post.isLiked
            if (post.isLiked) {
                post.likeCount++
                likeButton.setImageResource(R.drawable.likered)
            } else {
                post.likeCount--
                likeButton.setImageResource(R.drawable.like)
            }
            likeCountTextView.text = formatCount(post.likeCount)
            repository.updatePost(post)
        }

        // Обновление UI элементов
        private fun updateUI() {
            post?.let { currentPost ->
                likeCountTextView.text = formatCount(currentPost.likeCount)
                commentCountTextView.text = formatCount(currentPost.commentCount)
                shareCountTextView.text = formatCount(currentPost.shareCount)
                viewCountTextView.text = formatCount(currentPost.viewCount)
            }
        }

        // Открытие видео
        private fun openVideo(url: String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                itemView.context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(itemView.context, "Не удалось открыть видео", Toast.LENGTH_SHORT).show()
            }
        }

        private fun updateShareCount(post: Post) {
            post.shareCount++
            shareCountTextView.text = formatCount(post.shareCount)
            repository.updatePost(post)
        }

        // Отображение диалога с опциями
        private fun showOptionsDialog(post: Post) {
            val options = arrayOf("Редактировать", "Удалить")
            val dialog = AlertDialog.Builder(itemView.context)
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

        // Отображение диалога редактирования
        private fun showEditDialog(post: Post) {
            val currentText = if (isTextExpanded) text.text.toString() else post.text
            val editText = EditText(itemView.context)
            editText.setText(currentText)
            editText.setLines(8)

            val dialog = AlertDialog.Builder(itemView.context)
                .setTitle("Редактировать пост")
                .setView(editText)
                .setPositiveButton("Сохранить") { _, _ ->
                    val newText = editText.text.toString()
                    post.text = newText
                    text.text = if (isTextExpanded) newText else newText.split("\n")[0]
                    repository.updatePost(post)
                    Toast.makeText(itemView.context, "Пост отредактирован", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Отмена", null)
                .create()
            
            dialog.show()
        }

        // Отображение диалога подтверждения удаления
        private fun showDeleteConfirmation(post: Post) {
            val dialog = AlertDialog.Builder(itemView.context)
                .setTitle("Подтверждение")
                .setMessage("Вы уверены, что хотите удалить этот пост?")
                .setPositiveButton("Да") { _, _ ->
                    repository.deletePost(post.id)
                    Toast.makeText(itemView.context, "Пост удален", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Нет", null)
                .create()
            
            dialog.show()
        }

        // Форматирование чисел для отображения (K, M)
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

        fun updateTextExpansion(expanded: Boolean) {
            isTextExpanded = expanded
            post?.let { currentPost ->
                text.text = if (expanded) currentPost.text else currentPost.text.split("\n")[0]
                showMoreText.text = if (expanded) "Скрыть" else "Показать ещё"
                showMoreText.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_dark))
            }
        }
    }
}