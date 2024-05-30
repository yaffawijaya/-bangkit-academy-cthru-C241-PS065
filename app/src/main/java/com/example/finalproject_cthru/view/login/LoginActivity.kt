package com.example.finalproject_cthru.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_cthru.databinding.ActivityLoginBinding
import com.example.finalproject_cthru.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
//        setupAction()
        playAnimation()


        binding.accountCreateLabel.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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

//    private fun setupAction() {
//        binding.loginButton.setOnClickListener {
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//            viewModel.userLogin(email, password).observe(this) { result ->
//                when (result) {
//                    is Result.Loading -> { showLoading(true) }
//                    is Result.Success -> {
//                        showLoading(false)
//                        showToast(result.data.message)
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//                        this.finish()
//                    }
//                    is Result.Error -> {
//                        showLoading(false)
//                        showToast(result.error)
//                    }
//                }
//            }
//        }
//    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            startDelay = 1000
        }.start()

        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(400)
        val email_label = ObjectAnimator.ofFloat(binding.emailLabel, View.ALPHA, 1f).setDuration(400)
        val password_label = ObjectAnimator.ofFloat(binding.passwordLabel, View.ALPHA, 1f).setDuration(400)
        val title_email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(400)
        val login = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val title_password = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(400)
        val password = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val button = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(400)
        val account_label = ObjectAnimator.ofFloat(binding.accountLabel, View.ALPHA, 1f).setDuration(400)
        val account_create_label = ObjectAnimator.ofFloat(binding.accountCreateLabel, View.ALPHA, 1f).setDuration(400)
        val google_button = ObjectAnimator.ofFloat(binding.googleButton, View.ALPHA, 1f).setDuration(400)

        AnimatorSet().apply {
            playSequentially(
                image,
                title_email,
                login,
                email_label,
                title_password,
                password,
                password_label,
                button,
                account_label,
                account_create_label,
                google_button
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}