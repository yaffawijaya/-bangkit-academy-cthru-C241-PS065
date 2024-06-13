package com.example.finalproject_cthru.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject_cthru.data.remote.response.ArticlesItem
import com.example.finalproject_cthru.view.article.ArticleViewModel

class HomeViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<ArticlesItem>>()
    val articles: LiveData<List<ArticlesItem>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        // Manually setting the articles data
        setMockData()
    }

    private fun setMockData() {
        _isLoading.value = false

        val articlesList = ArticleViewModel.MOCK_ARTICLES

        _articles.value = articlesList
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}