package com.example.finalproject_cthru.view.article

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_cthru.MainActivity
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.data.remote.response.ArticlesItem
import com.example.finalproject_cthru.databinding.ActivityArticleBinding
import com.example.finalproject_cthru.view.adapter.ArticleAdapter
import com.example.finalproject_cthru.view.detailarticle.DetailArticleActivity
import com.example.finalproject_cthru.view.result.ResultActivity

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private val articleViewModel by viewModels<ArticleViewModel>()
    private lateinit var adapter: ArticleAdapter
    private var articleImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArticleAdapter()
        setupSearchBar()
        setupRecyclerView()
        observeLiveData()
    }

    private fun observeLiveData() {
        articleViewModel.articles.observe(this) { articles ->
            if (articles != null && articles.isNotEmpty()) {
                setArticles(articles)
            } else {
                // Handle empty or null list of articles
                Log.e("ArticleActivity", "No articles found")
                // You can also update the UI to indicate no articles found
//                binding.emptyView.visibility = View.VISIBLE
            }
        }
        articleViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setArticles(articles: List<ArticlesItem>) {
        // Do not recreate the adapter, just update the data
        adapter.submitList(articles)
//        binding.emptyView.visibility = if (articles.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvAdapter.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvAdapter.addItemDecoration(itemDecoration)

        binding.rvAdapter.adapter = adapter // Set the adapter once here

        adapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ArticlesItem) {
                val intent = Intent(this@ArticleActivity, DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.EXTRA_TITLE, data.title)
                intent.putExtra(DetailArticleActivity.EXTRA_DESCRIPTION, data.description)
                intent.putExtra(DetailArticleActivity.EXTRA_IMAGE_URI, data.urlToImage)
                startActivity(intent)
            }
        })
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(SearchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = binding.searchView.text.toString()
                // Trigger the search functionality here
                // articleViewModel.searchArticles(query)
                binding.searchView.hide()
                true
            }
            SearchBar.inflateMenu(R.menu.search)
            SearchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.home -> {
                        val intent = Intent(this@ArticleActivity, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
                true
            }
        }
    }
}
