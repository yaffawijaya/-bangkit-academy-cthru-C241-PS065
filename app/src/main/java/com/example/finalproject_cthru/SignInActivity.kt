package com.example.finalproject_cthru

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject_cthru.data.local.auth.AuthDataSource
import com.example.finalproject_cthru.data.local.auth.FirebaseAuthDataSource
import com.example.finalproject_cthru.data.remote.firebase.FirebaseService
import com.example.finalproject_cthru.data.remote.firebase.FirebaseServiceImpl
import com.example.finalproject_cthru.data.repository.UserRepository
import com.example.finalproject_cthru.data.repository.UserRepositoryImpl
import com.example.finalproject_cthru.databinding.ActivitySignInBinding
import com.example.finalproject_cthru.utils.GenericViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.example.finalproject_cthru.SignInViewModel
import com.example.finalproject_cthru.utils.proceedWhen

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding


    private val viewModel: SignInViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(SignInViewModel(r))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signUpInButton.setOnClickListener {
            doLogin()
        }
        binding.switchSignInUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        observeResult()

    }


    private fun observeResult() {
        viewModel.loginResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    navigateToMain()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                },
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passET.text.toString().trim()
            viewModel.doLogin(email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.emailEt.text.toString().trim()
        val password = binding.passET.text.toString().trim()

        return checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.passwordLayout)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.emailLayout.isErrorEnabled = true
            binding.emailLayout.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.isErrorEnabled = true
            binding.emailLayout.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.emailLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_pw_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_pw_lower)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }


//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
}