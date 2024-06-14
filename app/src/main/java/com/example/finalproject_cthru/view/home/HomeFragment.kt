package com.example.finalproject_cthru.view.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.data.remote.response.ArticlesItem
import com.example.finalproject_cthru.databinding.FragmentHomeBinding
import com.example.finalproject_cthru.view.adapter.ArticleAdapter
import com.example.finalproject_cthru.view.article.ArticleActivity
import com.example.finalproject_cthru.view.article.ArticleViewModel
import com.example.finalproject_cthru.view.detailarticle.DetailArticleActivity
import com.example.finalproject_cthru.view.onboarding.OnboardingActivity
import com.example.finalproject_cthru.view.profile.ProfileFragment
import com.example.finalproject_cthru.view.upload.UploadActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: ArticleAdapter

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

//        val articleButton: Button = binding.articleButton
//        articleButton.setOnClickListener {
//            val intent = Intent(activity, ArticleActivity::class.java)
//            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
//        }

        backHome()

        adapter = ArticleAdapter()
        setupRecyclerView()
        observeLiveData()

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Handle the result from ProfileEditActivity
            // Update UI or refresh data if needed
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.homeArticle.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.homeArticle.addItemDecoration(itemDecoration)

        binding.homeArticle.adapter = adapter // Set the adapter once here

        adapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ArticlesItem) {
                val intent = Intent(activity, DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.EXTRA_TITLE, data.title)
                intent.putExtra(DetailArticleActivity.EXTRA_DESCRIPTION, data.description)
                intent.putExtra(DetailArticleActivity.EXTRA_IMAGE_URI, data.urlToImage)
                startActivity(intent)
            }
        })
    }

    private fun observeLiveData() {
        homeViewModel.setMockData(requireContext())
        homeViewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles != null && articles.isNotEmpty()) {
                setArticles(articles)
            } else {
                // Handle empty or null list of articles
                Log.e("ArticleActivity", "No articles found")
                // You can also update the UI to indicate no articles found
//                binding.emptyView.visibility = View.VISIBLE
            }
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setArticles(articles: List<ArticlesItem>) {
        // Do not recreate the adapter, just update the data
        adapter.submitList(articles)
//        binding.emptyView.visibility = if (articles.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun backHome(){
        binding.detailMove.setOnClickListener{
            val intent = Intent(requireActivity(), ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val EDIT_PROFILE_REQUEST_CODE = 1
    }
}
