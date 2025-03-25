package com.example.z1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity3 : AppCompatActivity() {
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }

        postsRecyclerView = findViewById(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        postsAdapter = PostsAdapter()
        postsRecyclerView.adapter = postsAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    private val posts = listOf(1, 2, 3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postNumber = posts[position % posts.size]
        holder.bind(postNumber)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var likeCount = 156
        private var commentCount = 43
        private var shareCount = 28
        private var viewCount = 892
        private var isLiked = false

        private lateinit var likeButton: ImageButton
        private lateinit var likeCountTextView: TextView
        private lateinit var shareButton: ImageButton
        private lateinit var shareCountTextView: TextView
        private lateinit var viewCountTextView: TextView
        private lateinit var commentCountTextView: TextView
        private lateinit var text: TextView
        private var isTextExpanded = false
        private lateinit var showMoreText: TextView

        fun bind(postNumber: Int) {
            // Инициализация views
            likeButton = itemView.findViewById(R.id.like_button)
            likeCountTextView = itemView.findViewById(R.id.like_count)
            commentCountTextView = itemView.findViewById(R.id.comment_count)
            shareButton = itemView.findViewById(R.id.share_button)
            shareCountTextView = itemView.findViewById(R.id.share_count)
            viewCountTextView = itemView.findViewById(R.id.view_count)
            text = itemView.findViewById(R.id.post_description)
            showMoreText = itemView.findViewById(R.id.show_more_text)

            // Установка значений счетчиков в зависимости от номера поста
            when (postNumber) {
                1 -> {
                    likeCount = 156
                    commentCount = 43
                    shareCount = 28
                    viewCount = 892
                }
                2 -> {
                    likeCount = 324
                    commentCount = 87
                    shareCount = 65
                    viewCount = 2345
                }
                3 -> {
                    likeCount = 567
                    commentCount = 234
                    shareCount = 123
                    viewCount = 4567
                }
                else -> {
                    likeCount = 156
                    commentCount = 43
                    shareCount = 28
                    viewCount = 892
                }
            }

            // Установка аватара
            val avatarButton = itemView.findViewById<ImageButton>(R.id.avatar)
            avatarButton.setBackgroundResource(R.drawable.logo)

            // Установка названия пользователя
            val authorName = itemView.findViewById<TextView>(R.id.author_name)
            authorName.text = "Новости. Борисоглебский техникум промышленных и информационных технологий"

            // Установка даты публикации
            val publicationDate = itemView.findViewById<TextView>(R.id.publication_date)
            publicationDate.text = when (postNumber) {
                1 -> "19 марта в 13:36"
                2 -> "22 марта в 15:30"
                3 -> "25 марта в 09:45"
                else -> "19 марта в 13:36"
            }

            // Установка изображения поста
            val postImage = itemView.findViewById<ImageView>(R.id.post_image)
            postImage.setImageResource(when (postNumber) {
                1 -> R.drawable.krasavciki
                2 -> R.drawable.krasava2
                3 -> R.drawable.krasava3
                else -> R.drawable.krasavciki
            })

            // Установка текста поста
            text.text = when (postNumber) {
                1 -> "ЭСТАФЕТА ПАМЯТИ «ВО СЛАВУ ПОБЕДЫ!»\n\n17 марта, активисты «Движения Первых», волонтеры «Победы» и активисты ВПК «Соколы России» ГБПОУ ВО «БТПИТ» совместно с советниками директора по воспитанию и взаимодействию с детскими общественными объединениями С.В. Алехиной и Е.В. Сахаровой."
                2 -> "24 марта - день борьбы с туберкулезом.\n\nВ преддверии дня борьбы с туберкулезом активисты волонтерского объединения \"Лучик света\" организовали и провели среди студентов техникума занятие на тему: «Просветись!». Обучающимся предлагалось выполнить упражнения «История возникновения туберкулеза», «Что вызывает туберкулёз», «Какие основные симптомы туберкулеза», «Миф или реальность»."
                3 -> "9 марта 2025 года для студентов Борисоглебского техникума промышленных и информационных технологий была организованна и проведена профилактическая встреча с сотрудником ОГИБДД ОМВД России по г. Борисоглебск Семеновой О.А.\n\nВ ходе профилактической беседы инспектор по пропаганде ОГИБДД ОМВД России по г. Борисоглебск Семенова Ольга Александровна рассказала студентам об основных причинах дорожно-транспортных происшествий, в том числе с участием несовершеннолетних. Предупредила о недопустимости нарушений Правил дорожного движения, об административной ответственности несовершеннолетних за нарушение ПДД, управление транспортным средством водителями, не имеющим права управления, а также в состоянии алкогольного или наркотического опьянения."
                else -> "ЭСТАФЕТА ПАМЯТИ «ВО СЛАВУ ПОБЕДЫ!»\n\n17 марта, активисты «Движения Первых», волонтеры «Победы» и активисты ВПК «Соколы России» ГБПОУ ВО «БТПИТ» совместно с советниками директора по воспитанию и взаимодействию с детскими общественными объединениями С.В. Алехиной и Е.В. Сахаровой."
            }

            // Обработчики кликов
            likeButton.setOnClickListener {
                toggleLike()
            }

            shareButton.setOnClickListener {
                shareCount++
                updateUI()
            }

            showMoreText.setOnClickListener {
                if (!isTextExpanded) {
                    text.text = text.text.toString() + "\n" + when (postNumber) {
                        1 -> "Приняли участие в региональном\nпроекте «Эстафета Памяти «Во славу Победы!»\nна мемориальном комплексе Памяти и Славы у Вечного огня."
                        2 -> "По окончании занятия студенты пришли к выводу о том, что здоровый образ жизни, своевременное прохождение профилактических медицинских осмотров, а при необходимости своевременное и полноценное лечение является гарантом здоровья."
                        3 -> "Студенты активно задавали вопросы, высказывали своё мнение, интересовались действующим законодательством."
                        else -> "Приняли участие в региональном\nпроекте «Эстафета Памяти «Во славу Победы!»\nна мемориальном комплексе Памяти и Славы у Вечного огня."
                    }
                    showMoreText.text = "Скрыть"
                    showMoreText.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_dark))
                    isTextExpanded = true
                } else {
                    text.text = when (postNumber) {
                        1 -> "ЭСТАФЕТА ПАМЯТИ «ВО СЛАВУ ПОБЕДЫ!»\n\n17 марта, активисты «Движения Первых», волонтеры «Победы» и активисты ВПК «Соколы России» ГБПОУ ВО «БТПИТ» совместно с советниками директора по воспитанию и взаимодействию с детскими общественными объединениями С.В. Алехиной и Е.В. Сахаровой."
                        2 -> "24 марта - день борьбы с туберкулезом.\n\nВ преддверии дня борьбы с туберкулезом активисты волонтерского объединения \"Лучик света\" организовали и провели среди студентов техникума занятие на тему: «Просветись!». Обучающимся предлагалось выполнить упражнения «История возникновения туберкулеза», «Что вызывает туберкулёз», «Какие основные симптомы туберкулеза», «Миф или реальность»."
                        3 -> "9 марта 2025 года для студентов Борисоглебского техникума промышленных и информационных технологий была организованна и проведена профилактическая встреча с сотрудником ОГИБДД ОМВД России по г. Борисоглебск Семеновой О.А.\n\nВ ходе профилактической беседы инспектор по пропаганде ОГИБДД ОМВД России по г. Борисоглебск Семенова Ольга Александровна рассказала студентам об основных причинах дорожно-транспортных происшествий, в том числе с участием несовершеннолетних. Предупредила о недопустимости нарушений Правил дорожного движения, об административной ответственности несовершеннолетних за нарушение ПДД, управление транспортным средством водителями, не имеющим права управления, а также в состоянии алкогольного или наркотического опьянения."
                        else -> "ЭСТАФЕТА ПАМЯТИ «ВО СЛАВУ ПОБЕДЫ!»\n\n17 марта, активисты «Движения Первых», волонтеры «Победы» и активисты ВПК «Соколы России» ГБПОУ ВО «БТПИТ» совместно с советниками директора по воспитанию и взаимодействию с детскими общественными объединениями С.В. Алехиной и Е.В. Сахаровой."
                    }
                    showMoreText.text = "Показать ещё"
                    showMoreText.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_dark))
                    isTextExpanded = false
                }
            }

            updateUI()
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
    }
}