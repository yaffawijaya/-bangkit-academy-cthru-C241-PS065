package com.example.finalproject_cthru

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_cthru.data.local.auth.AuthDataSource
import com.example.finalproject_cthru.data.local.auth.FirebaseAuthDataSource
import com.example.finalproject_cthru.data.remote.firebase.FirebaseService
import com.example.finalproject_cthru.data.remote.firebase.FirebaseServiceImpl
import com.example.finalproject_cthru.data.repository.UserRepository
import com.example.finalproject_cthru.data.repository.UserRepositoryImpl
import com.example.finalproject_cthru.databinding.ActivitySignUpBinding
import com.example.finalproject_cthru.utils.GenericViewModelFactory
import com.example.finalproject_cthru.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val viewModel: SignUpViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(SignUpViewModel(r))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListeners()
    }


    private fun setClickListeners() {
        binding.signUpInButton.setOnClickListener {
            doRegister()
        }
        binding.switchSignInUp.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passET.text.toString().trim()
            val fullName = binding.nameEt.text.toString().trim()
            proceedRegister(email, password, fullName)
        }
    }

    private fun proceedRegister(
        email: String,
        password: String,
        fullName: String,
    ) {
        viewModel.doRegister(email, fullName, password).observe(this) {
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

    private fun navigateToLogin() {
        startActivity(
            Intent(this, SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun isFormValid(): Boolean {
        val password = binding.passET.text.toString().trim()
        val confirmPassword = binding.confirmPassEt.text.toString().trim()
        val fullName = binding.nameEt.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.passwordLayout) &&
                checkPasswordValidation(confirmPassword, binding.confirmPasswordLayout) &&
                checkPwdAndConfirmPwd(password, confirmPassword)
    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.nameLayout.isErrorEnabled = true
            binding.nameLayout.error = getString(R.string.text_error_name_empty)
            false
        } else {
            binding.nameLayout.isErrorEnabled = false
            true
        }
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
        textInputLayout: TextInputLayout,
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

    private fun checkPwdAndConfirmPwd(
        password: String,
        confirmPassword: String,
    ): Boolean {
        return if (password != confirmPassword) {
            binding.passwordLayout.isErrorEnabled = true
            binding.passwordLayout.error =
                getString(R.string.text_pw_nomatch)
            binding.confirmPasswordLayout.isErrorEnabled = true
            binding.confirmPasswordLayout.error =
                getString(R.string.text_pw_nomatch)
            false
        } else {
            binding.passwordLayout.isErrorEnabled = false
            binding.confirmPasswordLayout.isErrorEnabled = false
            true
        }
    }
}