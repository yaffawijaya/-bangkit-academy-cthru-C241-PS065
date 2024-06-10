package com.example.finalproject_cthru.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_cthru.BuildConfig
import com.example.finalproject_cthru.MainActivity
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.SignInActivity
import com.example.finalproject_cthru.SignUpActivity
import com.example.finalproject_cthru.databinding.ActivityLoginBinding
import com.example.finalproject_cthru.databinding.ActivitySignInBinding
import com.example.finalproject_cthru.view.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
//        setupAction()
        setupAction2()
        playAnimation()

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

    private fun setupAction2() {
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Redirect to RegisterActivity
        binding.accountCreateLabel.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Login Using Email & Password
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Check if we need to link with Google
//                        linkWithGoogleIfNeeded()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("SignInFailed", task.exception.toString())
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        // Login Using Google Button
//        val currentUser = firebaseAuth.currentUser
//        if (currentUser != null) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//        binding.googleButton.setOnClickListener {
//            signIn()
//        }
    }

//    private fun signIn() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(BuildConfig.AUTH_GOOGLE)
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                val idToken = account.idToken!!
//                val credential = GoogleAuthProvider.getCredential(idToken, null)
//
//                val currentUser = firebaseAuth.currentUser
//                if (currentUser != null) {
//                    // Check if Google is already linked
//                    if (!isProviderLinked(currentUser, GoogleAuthProvider.PROVIDER_ID)) {
//                        linkAccounts(credential)
//                    } else {
//                        // Google is already linked, no need to link again
//                        Toast.makeText(this, "Google is already linked", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this, MainActivity::class.java))
//                        finish()
//                    }
//                } else {
//                    // Sign in with Google credential
//                    firebaseAuthWithGoogle(credential)
//                }
//            } catch (e: ApiException) {
//                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
//                Log.d("SignInFailed", "Google sign in failed: ${e.message}")
//            }
//        }
//    }

//    private fun firebaseAuthWithGoogle(credential: AuthCredential) {
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                val user = firebaseAuth.currentUser
//                Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            } else {
//                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun linkWithGoogleIfNeeded() {
//        val user = firebaseAuth.currentUser
//        if (user != null) {
//            // Check if Google is already linked
//            if (!isProviderLinked(user, GoogleAuthProvider.PROVIDER_ID)) {
//                signIn()
//            } else {
//                // Google is already linked, no need to link again
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//        }
//    }
//
//    private fun linkAccounts(credential: AuthCredential) {
//        val user = firebaseAuth.currentUser
//        user?.linkWithCredential(credential)?.addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                val linkedUser = task.result?.user
//                Toast.makeText(this, "Accounts linked as ${linkedUser?.displayName}", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            } else {
//                Toast.makeText(this, "Account linking failed", Toast.LENGTH_SHORT).show()
//                Log.d("LinkFailed", task.exception.toString())
//            }
//        }
//    }
//
//    private fun isProviderLinked(user: FirebaseUser, providerId: String): Boolean {
//        for (profile in user.providerData) {
//            if (profile.providerId == providerId) {
//                return true
//            }
//        }
//        return false
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
//        val google_button = ObjectAnimator.ofFloat(binding.googleButton, View.ALPHA, 1f).setDuration(400)

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
//                google_button
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

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}