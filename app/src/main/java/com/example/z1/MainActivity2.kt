package com.example.z1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.EditText
import android.app.AlertDialog
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.z1.model.Post
import com.example.z1.repository.JsonPostRepository

/**
 * Основная активность приложения
 * Отображает ленту новостей с тремя постами
 * Реализует функционал лайков, репостов, просмотров и редактирования постов
 */
class MainActivity2 : AppCompatActivity() {

    private lateinit var repository: JsonPostRepository
    private var posts = mutableListOf<Post>()
    private var isTextExpanded1 = false
    private var isTextExpanded2 = false
    private var isTextExpanded3 = false

    // Счетчики
    private var likeCount = 156
    private var commentCount = 43
    private var shareCount = 28
    private var viewCount = 892
    private var isLiked = false

    private var likeCount2 = 324
    private var commentCount2 = 87
    private var shareCount2 = 65
    private var viewCount2 = 2345
    private var isLiked2 = false

    private var likeCount3 = 567
    private var commentCount3 = 234
    private var shareCount3 = 123
    private var viewCount3 = 4567
    private var isLiked3 = false

    // UI элементы
    private lateinit var likeButton: ImageButton
    private lateinit var likeCountTextView: TextView
    private lateinit var shareButton: ImageButton
    private lateinit var shareCountTextView: TextView
    private lateinit var viewCountTextView: TextView
    private lateinit var commentCountTextView: TextView
    private lateinit var text1: TextView
    private lateinit var avatarButton: ImageButton

    private lateinit var likeButton2: ImageButton
    private lateinit var likeCountTextView2: TextView
    private lateinit var shareButton2: ImageButton
    private lateinit var shareCountTextView2: TextView
    private lateinit var viewCountTextView2: TextView
    private lateinit var commentCountTextView2: TextView
    private lateinit var publicationDate2: TextView

    private lateinit var likeButton3: ImageButton
    private lateinit var likeCountTextView3: TextView
    private lateinit var shareButton3: ImageButton
    private lateinit var shareCountTextView3: TextView
    private lateinit var viewCountTextView3: TextView
    private lateinit var commentCountTextView3: TextView
    private lateinit var publicationDate3: TextView

    // Полные тексты постов
    private var fullText1 = "ЭСТАФЕТА ПАМЯТИ «ВО СЛАВУ ПОБЕДЫ!»\n\n17 марта, активисты «Движения Первых», волонтеры «Победы» и активисты ВПК «Соколы России» ГБПОУ ВО «БТПИТ» совместно с советниками директора по воспитанию и взаимодействию с детскими общественными объединениями С.В. Алехиной и Е.В. Сахаровой.\nПриняли участие в региональном проекте «Эстафета Памяти «Во славу Победы!» на мемориальном комплексе Памяти и Славы у Вечного огня."
    private var fullText2 = "24 марта - день борьбы с туберкулезом.\n\nПо окончании занятия студенты пришли к выводу о том, что здоровый образ жизни, своевременное прохождение профилактических медицинских осмотров, а при необходимости своевременное и полноценное лечение является гарантом здоровья."
    private var fullText3 = "9 марта 2025 года для студентов Борисоглебского техникума промышленных и информационных технологий была организованна и проведена профилактическая встреча с сотрудником ОГИБДД ОМВД России по г. Борисоглебск Семеновой О.А. В ходе профилактической беседы инспектор по пропаганде ОГИБДД ОМВД России по г. Борисоглебск Семенова Ольга Александровна рассказала студентам об основных причинах дорожно-транспортных происшествий, в том числе с участием несовершеннолетних. Предупредила о недопустимости нарушений Правил дорожного движения, об административной ответственности несовершеннолетних за нарушение ПДД, управление транспортным средством водителями, не имеющим права управления, а также в состоянии алкогольного или наркотического опьянения.\nСтуденты активно задавали вопросы, высказывали своё мнение, интересовались действующим законодательством."

    // Дополнительные UI элементы
    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var showMoreText1: TextView
    private lateinit var showMoreText2: TextView
    private lateinit var showMoreText3: TextView

    private lateinit var avatarButton2: ImageButton
    private lateinit var avatarButton3: ImageButton

    // Кнопки дополнительных опций для каждого поста
    private lateinit var moreOptionsButton: ImageButton
    private lateinit var moreOptionsButton2: ImageButton
    private lateinit var moreOptionsButton3: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        repository = JsonPostRepository(this)
        posts = repository.getAllPosts().toMutableList()

        // Настройка системных отступов
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

        // Инициализация UI элементов
        initializeViews()
        
        // Настройка обработчиков событий
        setupClickListeners()
        
        // Установка данных из репозитория
        setupPostData()
        
        setupListeners()
        setupMoreOptionsButtons()
        updateUI()
    }

    private fun setupPostData() {
        posts.forEach { post ->
            when (post.id) {
                1 -> {
                    text1.text = if (isTextExpanded1) post.text else post.text.split("\n")[0]
                    publicationDate2.text = post.publicationDate
                }
                2 -> {
                    text2.text = if (isTextExpanded2) post.text else post.text.split("\n")[0]
                    publicationDate2.text = post.publicationDate
                }
                3 -> {
                    text3.text = if (isTextExpanded3) post.text else post.text.split("\n")[0]
                    publicationDate3.text = post.publicationDate
                }
            }
        }
    }

    // Инициализация всех UI элементов
    private fun initializeViews() {
        // Инициализация элементов
        likeButton = findViewById(R.id.like_button)
        likeCountTextView = findViewById(R.id.like_count)
        commentCountTextView = findViewById(R.id.comment_count)
        shareButton = findViewById(R.id.share_button)
        shareCountTextView = findViewById(R.id.share_count)
        viewCountTextView = findViewById(R.id.view_count)
        text1 = findViewById(R.id.post_description1)
        showMoreText1 = findViewById(R.id.show_more_text1)

        likeButton2 = findViewById(R.id.like_button2)
        likeCountTextView2 = findViewById(R.id.like_count2)
        commentCountTextView2 = findViewById(R.id.comment_count2)
        shareButton2 = findViewById(R.id.share_button2)
        shareCountTextView2 = findViewById(R.id.share_count2)
        viewCountTextView2 = findViewById(R.id.view_count2)
        text2 = findViewById(R.id.post_description2)
        showMoreText2 = findViewById(R.id.show_more_text2)
        publicationDate2 = findViewById(R.id.publication_date2)

        likeButton3 = findViewById(R.id.like_button3)
        likeCountTextView3 = findViewById(R.id.like_count3)
        commentCountTextView3 = findViewById(R.id.comment_count3)
        shareButton3 = findViewById(R.id.share_button3)
        shareCountTextView3 = findViewById(R.id.share_count3)
        viewCountTextView3 = findViewById(R.id.view_count3)
        text3 = findViewById(R.id.post_description3)
        showMoreText3 = findViewById(R.id.show_more_text3)
        publicationDate3 = findViewById(R.id.publication_date3)

        // Инициализация кнопок аватаров
        avatarButton = findViewById(R.id.avatar)
        avatarButton2 = findViewById(R.id.avatar2)
        avatarButton3 = findViewById(R.id.avatar3)

        // Инициализация кнопок дополнительных опций
        moreOptionsButton = findViewById(R.id.more_options)
        moreOptionsButton2 = findViewById(R.id.more_options2)
        moreOptionsButton3 = findViewById(R.id.more_options3)
    }

    // Настройка обработчиков нажатий на кнопки
    private fun setupClickListeners() {
        // Обработчики для кнопок аватаров
        avatarButton.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        avatarButton2.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        avatarButton3.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Обработчики для кнопок "Показать ещё"
        setupShowMoreListeners()
    }

    // Настройка обработчиков для кнопок "Показать ещё"
    private fun setupShowMoreListeners() {
        showMoreText1.setOnClickListener {
            toggleTextExpansion(1)
        }

        showMoreText2.setOnClickListener {
            toggleTextExpansion(2)
        }

        showMoreText3.setOnClickListener {
            toggleTextExpansion(3)
        }
    }

    // Переключение состояния развернутости текста
    private fun toggleTextExpansion(postNumber: Int) {
        val post = posts.find { it.id == postNumber }
        if (post != null) {
            when (postNumber) {
                1 -> {
                    if (!isTextExpanded1) {
                        text1.text = post.text
                        showMoreText1.text = "Скрыть"
                        showMoreText1.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                        isTextExpanded1 = true
                    } else {
                        text1.text = post.text.split("\n")[0]
                        showMoreText1.text = "Показать ещё"
                        showMoreText1.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                        isTextExpanded1 = false
                    }
                }
                2 -> {
                    if (!isTextExpanded2) {
                        text2.text = post.text
                        showMoreText2.text = "Скрыть"
                        showMoreText2.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                        isTextExpanded2 = true
                    } else {
                        text2.text = post.text.split("\n")[0]
                        showMoreText2.text = "Показать ещё"
                        showMoreText2.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                        isTextExpanded2 = false
                    }
                }
                3 -> {
                    if (!isTextExpanded3) {
                        text3.text = post.text
                        showMoreText3.text = "Скрыть"
                        showMoreText3.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                        isTextExpanded3 = true
                    } else {
                        text3.text = post.text.split("\n")[0]
                        showMoreText3.text = "Показать ещё"
                        showMoreText3.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                        isTextExpanded3 = false
                    }
                }
            }
        }
    }

    // Настройка обработчиков для кнопок лайков и репостов
    private fun setupListeners() {
        // Обработчики
        likeButton.setOnClickListener {
            toggleLike(1)
        }
        shareButton.setOnClickListener {
            updateShareCount(1)
        }

        likeButton2.setOnClickListener {
            toggleLike(2)
        }
        shareButton2.setOnClickListener {
            updateShareCount(2)
        }

        likeButton3.setOnClickListener {
            toggleLike(3)
        }
        shareButton3.setOnClickListener {
            updateShareCount(3)
        }
    }

    // Переключение состояния лайка
    private fun toggleLike(postId: Int) {
        val post = posts.find { it.id == postId }
        if (post != null) {
            post.isLiked = !post.isLiked
            if (post.isLiked) {
                post.likeCount++
            } else {
                post.likeCount--
            }
            repository.updatePost(post)
            updateUI()
        }
    }

    private fun updateShareCount(postId: Int) {
        val post = posts.find { it.id == postId }
        if (post != null) {
            post.shareCount++
            repository.updatePost(post)
            updateUI()
        }
    }

    // Обновление UI элементов
    private fun updateUI() {
        posts.forEach { post ->
            when (post.id) {
                1 -> {
                    likeCountTextView.text = formatCount(post.likeCount)
                    commentCountTextView.text = formatCount(post.commentCount)
                    shareCountTextView.text = formatCount(post.shareCount)
                    viewCountTextView.text = formatCount(post.viewCount)
                    likeButton.setImageResource(if (post.isLiked) R.drawable.likered else R.drawable.like)
                }
                2 -> {
                    likeCountTextView2.text = formatCount(post.likeCount)
                    commentCountTextView2.text = formatCount(post.commentCount)
                    shareCountTextView2.text = formatCount(post.shareCount)
                    viewCountTextView2.text = formatCount(post.viewCount)
                    likeButton2.setImageResource(if (post.isLiked) R.drawable.likered else R.drawable.like)
                }
                3 -> {
                    likeCountTextView3.text = formatCount(post.likeCount)
                    commentCountTextView3.text = formatCount(post.commentCount)
                    shareCountTextView3.text = formatCount(post.shareCount)
                    viewCountTextView3.text = formatCount(post.viewCount)
                    likeButton3.setImageResource(if (post.isLiked) R.drawable.likered else R.drawable.like)
                }
            }
        }
    }

    // Форматирование чисел
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

    // Настройка обработчиков для кнопок дополнительных опций
    private fun setupMoreOptionsButtons() {
        moreOptionsButton.setOnClickListener { showOptionsDialog(1) }
        moreOptionsButton2.setOnClickListener { showOptionsDialog(2) }
        moreOptionsButton3.setOnClickListener { showOptionsDialog(3) }
    }

    // Отображение диалога с опциями для поста
    private fun showOptionsDialog(postNumber: Int) {
        val options = arrayOf("Редактировать", "Удалить")
        val dialog = AlertDialog.Builder(this)
            .setTitle("Выберите действие")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditDialog(postNumber)
                    1 -> showDeleteConfirmation(postNumber)
                }
            }
            .create()
        
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(getColor(android.R.color.holo_blue_light))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(getColor(android.R.color.holo_blue_light))
        }
        
        dialog.show()
    }

    // Отображение диалога подтверждения удаления
    private fun showDeleteConfirmation(postNumber: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Подтверждение")
            .setMessage("Вы уверены, что хотите удалить этот пост?")
            .setPositiveButton("Да") { _, _ ->
                deletePost(postNumber)
            }
            .setNegativeButton("Нет", null)
            .create()
        
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(getColor(android.R.color.holo_blue_light))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(getColor(android.R.color.holo_blue_light))
        }
        
        dialog.show()
    }

    // Удаление поста
    private fun deletePost(postNumber: Int) {
        repository.deletePost(postNumber)
        posts.removeIf { it.id == postNumber }
        val postLayout = when (postNumber) {
            1 -> findViewById<View>(R.id.post_layout1)
            2 -> findViewById<View>(R.id.post_layout2)
            3 -> findViewById<View>(R.id.post_layout3)
            else -> null
        }
        
        postLayout?.visibility = View.GONE
        Toast.makeText(this, "Пост удален", Toast.LENGTH_SHORT).show()
    }

    // Отображение диалога редактирования поста
    private fun showEditDialog(postNumber: Int) {
        val post = posts.find { it.id == postNumber }
        if (post != null) {
            val currentText = if (when (postNumber) {
                1 -> isTextExpanded1
                2 -> isTextExpanded2
                3 -> isTextExpanded3
                else -> false
            }) {
                when (postNumber) {
                    1 -> text1.text.toString()
                    2 -> text2.text.toString()
                    3 -> text3.text.toString()
                    else -> ""
                }
            } else {
                post.text
            }

            val editText = EditText(this)
            editText.setText(currentText)
            editText.setLines(8)

            val dialog = AlertDialog.Builder(this)
                .setTitle("Редактировать пост")
                .setView(editText)
                .setPositiveButton("Сохранить") { _, _ ->
                    val newText = editText.text.toString()
                    post.text = newText
                    repository.updatePost(post)
                    when (postNumber) {
                        1 -> {
                            text1.text = if (isTextExpanded1) newText else newText.split("\n")[0]
                        }
                        2 -> {
                            text2.text = if (isTextExpanded2) newText else newText.split("\n")[0]
                        }
                        3 -> {
                            text3.text = if (isTextExpanded3) newText else newText.split("\n")[0]
                        }
                    }
                    Toast.makeText(this, "Пост отредактирован", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Отмена", null)
                .create()
            
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(getColor(android.R.color.holo_blue_light))
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(getColor(android.R.color.holo_blue_light))
            }
            
            dialog.show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
