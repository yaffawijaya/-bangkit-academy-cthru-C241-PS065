package com.example.finalproject_cthru.view.detailarticle

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.ActivityDetailArticleBinding
import com.example.finalproject_cthru.view.result.ResultActivity

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var detailViewModel: DetailArticleViewModel
    private lateinit var username: String
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val image = intent.getStringExtra(EXTRA_IMAGE_URI)

        Glide.with(binding.root)
            .load(image)
            .apply(RequestOptions().override(1200, 800).placeholder(R.drawable.ic_place_holder))
            .into(binding.articleImage)


        // Use the title and description as needed
        binding.titleArticle.text = title
        binding.descriptionArticle.text = description

        backHome()

    }

    private fun backHome(){
        binding.btnBackDetailarticle.setOnClickListener{
            val resultIntent = Intent()
            // Add any result data if needed
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }


}