package com.example.finalproject_cthru.view.detailarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject_cthru.data.remote.response.ArticlesItem

class DetailArticleViewModel: ViewModel() {

    private val _datauser = MutableLiveData<ArticlesItem>()
    val datauser: LiveData<ArticlesItem> = _datauser
}