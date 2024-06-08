package com.example.finalproject_cthru.view.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject_cthru.data.remote.response.ArticleResponse
import com.example.finalproject_cthru.data.remote.response.ArticlesItem
import com.example.finalproject_cthru.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<ArticlesItem>>() // MutableLiveData<List<ArticlesItem>>
    val articles: LiveData<List<ArticlesItem>> = _articles // LiveData<List<ArticlesItem>>

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getNews()
    }

    fun getNews() {
        _isLoading.value = true
        val service = ApiConfig.getApiService3().getNews("health", "id")
        service.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.d("ArticleViewModel", "Response: $responseBody")
                    val articlesList = responseBody.articles.orEmpty().filterNotNull() // Filter out null items
                    _articles.value = articlesList
                } else {
                    Log.e("ArticleViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("ArticleViewModel", "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ArticleViewModel"
    }
}
