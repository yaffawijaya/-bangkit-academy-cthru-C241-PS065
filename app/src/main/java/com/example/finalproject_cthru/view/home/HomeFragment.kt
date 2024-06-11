package com.example.finalproject_cthru.view.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.FragmentHomeBinding
import com.example.finalproject_cthru.view.article.ArticleActivity
import com.example.finalproject_cthru.view.detailarticle.DetailArticleActivity
import com.example.finalproject_cthru.view.onboarding.OnboardingActivity
import com.example.finalproject_cthru.view.profile.ProfileFragment
import com.example.finalproject_cthru.view.upload.UploadActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashboardViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Use binding to access the button
        val scanButton: Button = binding.scanButton
        scanButton.setOnClickListener {
            val intent = Intent(activity, UploadActivity::class.java)
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
        }

        val articleButton: Button = binding.articleButton
        articleButton.setOnClickListener {
            val intent = Intent(activity, DetailArticleActivity::class.java)
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Handle the result from ProfileEditActivity
            // Update UI or refresh data if needed
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EDIT_PROFILE_REQUEST_CODE = 1
    }
}
