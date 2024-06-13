package com.example.finalproject_cthru.view.detailarticle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var detailViewModel: DetailArticleViewModel
    private lateinit var username: String
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        const val EXTRA_USER = "extra_user"
    }


}