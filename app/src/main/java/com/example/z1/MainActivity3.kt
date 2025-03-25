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

class MainActivity3 : AppCompatActivity() {
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
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
        private var isTextExpanded = false
        private var fullText = ""
        private var videoUrl: String? = null

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
        private lateinit var videoThumbnail: ImageView
        private lateinit var playButton: ImageView

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
            moreOptionsButton = itemView.findViewById(R.id.more_options)
            videoContainer = itemView.findViewById(R.id.video_container)
            playButton = itemView.findViewById(R.id.play_button)

            when (postNumber) {
                1 -> {
                    likeCount = 156
                    commentCount = 43
                    shareCount = 28
                    viewCount = 892
                    fullText = "ЭСТАФЕТА ПАМЯТИ «ВО СЛАВУ ПОБЕДЫ!»\n\n17 марта, активисты «Движения Первых», волонтеры «Победы» и активисты ВПК «Соколы России» ГБПОУ ВО «БТПИТ» совместно с советниками директора по воспитанию и взаимодействию с детскими общественными объединениями С.В. Алехиной и Е.В. Сахаровой.\nПриняли участие в региональном проекте «Эстафета Памяти «Во славу Победы!» на мемориальном комплексе Памяти и Славы у Вечного огня."
                    videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
                }
                2 -> {
                    likeCount = 324
                    commentCount = 87
                    shareCount = 65
                    viewCount = 2345
                    fullText = "24 марта - день борьбы с туберкулезом.\n\nВ преддверии дня борьбы с туберкулезом активисты волонтерского объединения \"Лучик света\" организовали и провели среди студентов техникума занятие на тему: «Просветись!». Обучающимся предлагалось выполнить упражнения «История возникновения туберкулеза», «Что вызывает туберкулёз», «Какие основные симптомы туберкулеза», «Миф или реальность».\nПо окончании занятия студенты пришли к выводу о том, что здоровый образ жизни, своевременное прохождение профилактических медицинских осмотров, а при необходимости своевременное и полноценное лечение является гарантом здоровья."
                    videoUrl = null
                }
                3 -> {
                    likeCount = 567
                    commentCount = 234
                    shareCount = 123
                    viewCount = 4567
                    fullText = "9 марта 2025 года для студентов Борисоглебского техникума промышленных и информационных технологий была организованна и проведена профилактическая встреча с сотрудником ОГИБДД ОМВД России по г. Борисоглебск Семеновой О.А.\n\nВ ходе профилактической беседы инспектор по пропаганде ОГИБДД ОМВД России по г. Борисоглебск Семенова Ольга Александровна рассказала студентам об основных причинах дорожно-транспортных происшествий, в том числе с участием несовершеннолетних. Предупредила о недопустимости нарушений Правил дорожного движения, об административной ответственности несовершеннолетних за нарушение ПДД, управление транспортным средством водителями, не имеющим права управления, а также в состоянии алкогольного или наркотического опьянения.\nСтуденты активно задавали вопросы, высказывали своё мнение, интересовались действующим законодательством."
                    videoUrl = "https://www.youtube.com/watch?v=Kh_haVVhdjA"
                }
            }

            if (videoUrl != null) {
                videoContainer.visibility = View.VISIBLE
                videoContainer.setOnClickListener { openVideo(videoUrl!!) }
                playButton.setOnClickListener { openVideo(videoUrl!!) }
            } else {
                videoContainer.visibility = View.GONE
            }

            val avatarButton = itemView.findViewById<ImageButton>(R.id.avatar)
            avatarButton.setBackgroundResource(R.drawable.logo)

            val authorName = itemView.findViewById<TextView>(R.id.author_name)
            authorName.text = "Новости. Борисоглебский техникум промышленных и информационных технологий"

            val publicationDate = itemView.findViewById<TextView>(R.id.publication_date)
            publicationDate.text = when (postNumber) {
                1 -> "19 марта в 13:36"
                2 -> "22 марта в 15:30"
                3 -> "25 марта в 09:45"
                else -> "19 марта в 13:36"
            }

            val postImage = itemView.findViewById<ImageView>(R.id.post_image)
            if (postNumber == 3) {
                postImage.visibility = View.GONE
                videoContainer.visibility = View.VISIBLE
                videoContainer.setBackgroundResource(R.drawable.krasava3)
                videoContainer.setOnClickListener { openVideo(videoUrl!!) }
                playButton.setOnClickListener { openVideo(videoUrl!!) }
            } else {
                postImage.visibility = View.VISIBLE
                videoContainer.visibility = View.GONE
                postImage.setImageResource(when (postNumber) {
                    1 -> R.drawable.krasavciki
                    2 -> R.drawable.krasava2
                    else -> R.drawable.krasavciki
                })
            }

            text.text = if (isTextExpanded) fullText else fullText.split("\n")[0]

            likeButton.setOnClickListener {
                toggleLike()
            }

            shareButton.setOnClickListener {
                shareCount++
                updateUI()
            }

            showMoreText.setOnClickListener {
                if (!isTextExpanded) {
                    text.text = fullText
                    showMoreText.text = "Скрыть"
                    showMoreText.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_dark))
                    isTextExpanded = true
                } else {
                    text.text = fullText.split("\n")[0]
                    showMoreText.text = "Показать ещё"
                    showMoreText.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_dark))
                    isTextExpanded = false
                }
            }

            moreOptionsButton.setOnClickListener {
                showOptionsDialog(postNumber)
            }

            updateUI()
        }

        private fun showOptionsDialog(postNumber: Int) {
            val options = arrayOf("Редактировать", "Удалить")
            val dialog = AlertDialog.Builder(itemView.context)
                .setTitle("Выберите действие")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> showEditDialog(postNumber)
                        1 -> showDeleteConfirmation(postNumber)
                    }
                }
                .create()
            
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(itemView.context.getColor(android.R.color.holo_blue_light))
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(itemView.context.getColor(android.R.color.holo_blue_light))
            }
            
            dialog.show()
        }

        private fun showDeleteConfirmation(postNumber: Int) {
            val dialog = AlertDialog.Builder(itemView.context)
                .setTitle("Подтверждение")
                .setMessage("Вы уверены, что хотите удалить этот пост?")
                .setPositiveButton("Да") { _, _ ->
                    deletePost(postNumber)
                }
                .setNegativeButton("Нет", null)
                .create()
            
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(itemView.context.getColor(android.R.color.holo_blue_light))
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(itemView.context.getColor(android.R.color.holo_blue_light))
            }
            
            dialog.show()
        }

        private fun deletePost(postNumber: Int) {
            Toast.makeText(itemView.context, "Пост удален", Toast.LENGTH_SHORT).show()
        }

        private fun showEditDialog(postNumber: Int) {
            val currentText = if (isTextExpanded) text.text.toString() else fullText

            val editText = EditText(itemView.context)
            editText.setText(currentText)
            editText.setLines(8)

            val dialog = AlertDialog.Builder(itemView.context)
                .setTitle("Редактировать пост")
                .setView(editText)
                .setPositiveButton("Сохранить") { _, _ ->
                    val newText = editText.text.toString()
                    fullText = newText
                    text.text = if (isTextExpanded) newText else newText.split("\n")[0]
                    Toast.makeText(itemView.context, "Пост отредактирован", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Отмена", null)
                .create()
            
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(itemView.context.getColor(android.R.color.holo_blue_light))
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(itemView.context.getColor(android.R.color.holo_blue_light))
            }
            
            dialog.show()
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

        private fun openVideo(url: String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                itemView.context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(itemView.context, "Не удалось открыть видео", Toast.LENGTH_SHORT).show()
            }
        }
    }
}