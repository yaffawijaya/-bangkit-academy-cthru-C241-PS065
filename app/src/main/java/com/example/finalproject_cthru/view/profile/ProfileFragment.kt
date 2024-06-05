package com.example.finalproject_cthru.view.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject_cthru.MainActivity
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.data.local.auth.AuthDataSource
import com.example.finalproject_cthru.data.local.auth.FirebaseAuthDataSource
import com.example.finalproject_cthru.data.remote.firebase.FirebaseService
import com.example.finalproject_cthru.data.remote.firebase.FirebaseServiceImpl
import com.example.finalproject_cthru.data.repository.UserRepository
import com.example.finalproject_cthru.data.repository.UserRepositoryImpl
import com.example.finalproject_cthru.databinding.FragmentProfileBinding
import com.example.finalproject_cthru.utils.GenericViewModelFactory
import com.example.finalproject_cthru.utils.proceedWhen
import com.example.finalproject_cthru.view.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels {
        val user: FirebaseService = FirebaseServiceImpl()
        val userDataSource: AuthDataSource = FirebaseAuthDataSource(user)
        val userRepo: UserRepository = UserRepositoryImpl(userDataSource)
        GenericViewModelFactory.create(ProfileViewModel(userRepo))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root




        binding.buttonUbahPassword.setOnClickListener {
            changePasswordUser()
        }
        binding.logoutButton.setOnClickListener {
            logoutUser()
        }

        return root
    }


    private fun changeProfileName(fullName: String) {
        viewModel.changeProfile(fullName).observe(viewLifecycleOwner) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(requireContext(), getString(R.string.text_link_edit_profile_success), Toast.LENGTH_SHORT).show()
                    viewModel.changeEditMode()
                },
                doOnError = {
                    Toast.makeText(requireContext(), getString(R.string.text_link_edit_profile_failed), Toast.LENGTH_SHORT).show()
                },
            )
        }
    }

    private fun logoutUser() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog_logout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel_dialog)
        val btnLogout = dialog.findViewById<Button>(R.id.btn_logout_dialog)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnLogout.setOnClickListener {
            dialog.dismiss()
            viewModel.doLogout()
            navigateToHome()
        }
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }

    private fun navigateToHome() {
        startActivity(
            Intent(requireContext(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun changePasswordUser() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog_change_password)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewModel.changePassword()
        val backBtn: Button = dialog.findViewById(R.id.btn_back)
        backBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}