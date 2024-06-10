package com.example.finalproject_cthru.view.result

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.ActivityResultBinding
import com.example.finalproject_cthru.view.maps.MapsActivity
import com.example.finalproject_cthru.view.upload.UploadActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        ReceiveSetup()

        binding.backButton.setOnClickListener {
            val resultIntent = Intent()
            // Add any result data if needed
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.mapsButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ReceiveSetup() {
        val image = intent.getStringExtra(EXTRA_IMAGE_URI)!!

        val bundle = intent.extras

        val imageUri = Uri.parse(image)
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        val eyeConfidence = bundle?.getDouble(EXTRA_CONFIDENCE_EYE)
        val formattedEyeConfidence = String.format("%.2f%%", eyeConfidence?.times(100) ?: 0.0)
        Log.d("Confidence Score", "Eye Confidence: $formattedEyeConfidence")
        binding.eyeConfidenceResult.text = formattedEyeConfidence

        val cataractConfidence = bundle?.getDouble(EXTRA_CONFIDENCE_CATARACT)
        val formattedCataractConfidence =
            String.format("%.2f%%", cataractConfidence?.times(100) ?: 0.0)
        Log.d("Confidence Score", "Cataract Confidence: $formattedCataractConfidence")
        binding.cataractConfidenceResult.text = formattedCataractConfidence

        val cataractPrediction = bundle?.getString(EXTRA_PREDICT_CATARACT)
        binding.cataractPrediction.text = cataractPrediction
        if (cataractPrediction == "Cataract") {
            binding.cataractPrediction.setTextColor(resources.getColor(R.color.red))
        } else {
            binding.cataractPrediction.setTextColor(resources.getColor(R.color.blue))
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_CONFIDENCE_CATARACT = "extra_confidence_cataract"
        const val EXTRA_CONFIDENCE_EYE = "extra_confidence_eye"
        const val EXTRA_PREDICT_CATARACT = "extra_predict_cataract"
    }
}