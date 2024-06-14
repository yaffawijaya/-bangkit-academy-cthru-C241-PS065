package com.example.finalproject_cthru.view.article

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject_cthru.data.remote.response.ArticleResponse
import com.example.finalproject_cthru.data.remote.response.ArticlesItem
import com.example.finalproject_cthru.data.remote.response.Source
import com.example.finalproject_cthru.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

    class ArticleViewModel : ViewModel() {

        private val _articles = MutableLiveData<List<ArticlesItem>?>()
        val articles: MutableLiveData<List<ArticlesItem>?> = _articles

        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading: LiveData<Boolean> = _isLoading

        fun setMockData(context: Context) {
            _isLoading.value = false

//        val articlesList = ArticleViewModel.MOCK_ARTICLES
            val articlesList = ArticleResponse.setMockData(context)

            _articles.value = articlesList
        }

        companion object {
            private const val TAG = "ArticleViewModel"
        }
    }
