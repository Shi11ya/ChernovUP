package com.example.z1

import android.annotation.SuppressLint
import android.content.Intent
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

    // Первый пост
    private var likeCount = 156
    private var commentCount = 43
    private var shareCount = 28
    private var viewCount = 892
    private var isLiked = false

    // Второй пост
    private var likeCount2 = 324
    private var commentCount2 = 87
    private var shareCount2 = 65
    private var viewCount2 = 2345
    private var isLiked2 = false

    // Третий пост
    private var likeCount3 = 567
    private var commentCount3 = 234
    private var shareCount3 = 123
    private var viewCount3 = 4567
    private var isLiked3 = false

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

    private var isTextExpanded1 = false
    private var isTextExpanded2 = false
    private var isTextExpanded3 = false

    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var showMoreText1: TextView
    private lateinit var showMoreText2: TextView
    private lateinit var showMoreText3: TextView

    private lateinit var avatarButton2: ImageButton
    private lateinit var avatarButton3: ImageButton

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

        avatarButton = findViewById(R.id.avatar)
        avatarButton.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        avatarButton2 = findViewById(R.id.avatar2)
        avatarButton2.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        avatarButton3 = findViewById(R.id.avatar3)
        avatarButton3.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        showMoreText1.setOnClickListener {
            if (!isTextExpanded1) {
                text1.text = text1.text.toString() + "\nПриняли участие в региональном\n" +
                        "проекте «Эстафета Памяти «Во славу Победы!»\n" +
                        "на мемориальном комплексе Памяти и Славы у Вечного огня."
                showMoreText1.text = "Скрыть"
                showMoreText1.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                isTextExpanded1 = true
            } else {
                text1.text = "ЭСТАФЕТА ПАМЯТИ «ВО СЛАВУ ПОБЕДЫ!»\n\n17 марта, активисты «Движения Первых», волонтеры «Победы» и активисты ВПК «Соколы России» ГБПОУ ВО «БТПИТ» совместно с советниками директора по воспитанию и взаимодействию с детскими общественными объединениями С.В. Алехиной и Е.В. Сахаровой."
                showMoreText1.text = "Показать ещё"
                showMoreText1.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                isTextExpanded1 = false
            }
        }

        showMoreText2.setOnClickListener {
            if (!isTextExpanded2) {
                text2.text = text2.text.toString() + "\nПо окончании занятия студенты пришли к выводу о том, что здоровый образ жизни, своевременное прохождение профилактических медицинских осмотров, а при необходимости своевременное и полноценное лечение является гарантом здоровья."
                showMoreText2.text = "Скрыть"
                showMoreText2.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                isTextExpanded2 = true
            } else {
                text2.text = "24 марта - день борьбы с туберкулезом.\n\nВ преддверии дня борьбы с туберкулезом активисты волонтерского объединения \"Лучик света\" организовали и провели среди студентов техникума занятие на тему: «Просветись!». Обучающимся предлагалось выполнить упражнения «История возникновения туберкулеза», «Что вызывает туберкулёз», «Какие основные симптомы туберкулеза», «Миф или реальность»."
                showMoreText2.text = "Показать ещё"
                showMoreText2.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                isTextExpanded2 = false
            }
        }

        showMoreText3.setOnClickListener {
            if (!isTextExpanded3) {
                text3.text = text3.text.toString() + "\nСтуденты активно задавали вопросы, высказывали своё мнение, интересовались действующим законодательством."
                showMoreText3.text = "Скрыть"
                showMoreText3.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                isTextExpanded3 = true
            } else {
                text3.text = "9 марта 2025 года для студентов Борисоглебского техникума промышленных и информационных технологий была организованна и проведена профилактическая встреча с сотрудником ОГИБДД ОМВД России по г. Борисоглебск Семеновой О.А.\n\nВ ходе профилактической беседы инспектор по пропаганде ОГИБДД ОМВД России по г. Борисоглебск Семенова Ольга Александровна рассказала студентам об основных причинах дорожно-транспортных происшествий, в том числе с участием несовершеннолетних. Предупредила о недопустимости нарушений Правил дорожного движения, об административной ответственности несовершеннолетних за нарушение ПДД, управление транспортным средством водителями, не имеющим права управления, а также в состоянии алкогольного или наркотического опьянения."
                showMoreText3.text = "Показать ещё"
                showMoreText3.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                isTextExpanded3 = false
            }
        }

        // Set new dates for second and third posts
        publicationDate2.text = "22 марта в 15:30"
        publicationDate3.text = "25 марта в 09:45"

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


        likeButton2.setOnClickListener {
            toggleLike2()
        }
        shareButton2.setOnClickListener {
            shareCount2++
            updateUI()
        }


        likeButton3.setOnClickListener {
            toggleLike3()
        }
        shareButton3.setOnClickListener {
            shareCount3++
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

    private fun toggleLike2() {
        isLiked2 = !isLiked2
        if (isLiked2) {
            likeCount2++
            likeButton2.setImageResource(R.drawable.likered)
        } else {
            likeCount2--
            likeButton2.setImageResource(R.drawable.like)
        }
        updateUI()
    }

    private fun toggleLike3() {
        isLiked3 = !isLiked3
        if (isLiked3) {
            likeCount3++
            likeButton3.setImageResource(R.drawable.likered)
        } else {
            likeCount3--
            likeButton3.setImageResource(R.drawable.like)
        }
        updateUI()
    }

    private fun updateUI() {

        likeCountTextView.text = formatCount(likeCount)
        commentCountTextView.text = formatCount(commentCount)
        shareCountTextView.text = formatCount(shareCount)
        viewCountTextView.text = formatCount(viewCount)


        likeCountTextView2.text = formatCount(likeCount2)
        commentCountTextView2.text = formatCount(commentCount2)
        shareCountTextView2.text = formatCount(shareCount2)
        viewCountTextView2.text = formatCount(viewCount2)


        likeCountTextView3.text = formatCount(likeCount3)
        commentCountTextView3.text = formatCount(commentCount3)
        shareCountTextView3.text = formatCount(shareCount3)
        viewCountTextView3.text = formatCount(viewCount3)
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
