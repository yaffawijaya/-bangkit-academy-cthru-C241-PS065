package com.example.finalproject_cthru.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
//        setupAction()
        setupAction2()
        playAnimation()

    }

    private fun setupAction2(){

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
        val title_name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(400)
        val name = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val title_email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(400)
        val login = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val title_password = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(400)
        val password = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val title_confirm_password = ObjectAnimator.ofFloat(binding.confirmPasswordLabel, View.ALPHA, 1f).setDuration(400)
        val confirm_password = ObjectAnimator.ofFloat(binding.confirmpasswordEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val button = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(400)


        AnimatorSet().apply {
            playSequentially(
                image,
                title_name,
                name,
                title_email,
                login,
                title_password,
                password,
                title_confirm_password,
                confirm_password,
                button
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}