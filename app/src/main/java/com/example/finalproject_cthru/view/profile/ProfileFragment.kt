package com.example.finalproject_cthru.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.FragmentProfileBinding
import com.example.finalproject_cthru.view.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        displayUserInfo()

        binding.logoutButton.setOnClickListener {
            signOut()
        }

        return root
    }

    private fun displayUserInfo() {
        val user = firebaseAuth.currentUser
        user?.let {
            val name = it.displayName ?: "No Name"
            val email = it.email ?: "No Email"
            binding.name.text = name
            binding.email.text = email
        }
    }

    private fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish() // Menutup MainActivity setelah logout
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}