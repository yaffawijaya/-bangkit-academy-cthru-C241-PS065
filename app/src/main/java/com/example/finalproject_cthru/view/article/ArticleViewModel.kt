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

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

    class ArticleViewModel : ViewModel() {

        private val _articles = MutableLiveData<List<ArticlesItem>>()
        val articles: LiveData<List<ArticlesItem>> = _articles

        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading: LiveData<Boolean> = _isLoading

        init {
            // Initialize with mock data
            loadMockData()
        }

        private fun loadMockData() {
            _isLoading.value = false
            _articles.value = MOCK_ARTICLES
        }

        companion object {
            private const val TAG = "ArticleViewModel"

            // Define the mock data here
            val MOCK_ARTICLES = listOf(
                ArticlesItem(
                    publishedAt = "2021-10-19",
                    author = "sehatNegeriku",
                    urlToImage = "https://sehatnegeriku.kemkes.go.id/wp-content/uploads/2021/10/Screenshot_20211013-073434_YouTube-750x536.jpg",
                    description = "Gangguan penglihatan, terutama yang disebabkan oleh katarak, masih menjadi masalah utama di Indonesia. Data dari Kementerian Kesehatan menunjukkan bahwa 81% dari kasus kebutaan pada orang berusia 50 tahun ke atas disebabkan oleh katarak. Dalam konferensi pers terkait Hari Penglihatan Sedunia, dr. Maxi Rein Rondonuwu menyoroti bahwa gangguan penglihatan memengaruhi kualitas hidup, termasuk aspek fisik, mental, dan sosial. Upaya untuk menangani masalah ini terus dilakukan, termasuk melalui kampanye kesehatan dan pemanfaatan teknologi informasi. Masyarakat diimbau untuk menjaga kesehatan mata dan melakukan deteksi dini gangguan penglihatan.",
                    source = "Kementerian Kesehatan",
                    title = "Katarak Penyebab Terbanyak Gangguan Penglihatan di Indonesia - sehatNegeriku",
                    url = "https://sehatnegeriku.kemkes.go.id/baca/umum/20211012/5738714/katarak-penyebab-terbanyak-gangguan-penglihatan-di-indonesia/",
                    content = null
                ),
                ArticlesItem(
                    publishedAt = "2020-10-06",
                    author = "sehatNegeriku",
                    urlToImage = "https://sehatnegeriku.kemkes.go.id/wp-content/uploads/2021/01/Placeholder-Artikel-01-750x536.jpg",
                    description = "Katarak menjadi penyebab utama kebutaan di Indonesia, dengan angka mencapai 81% dari total kasus kebutaan. Data survei dari Perhimpunan Dokter Spesialis Mata Indonesia (Perdami) menunjukkan angka kebutaan nasional mencapai 3% pada populasi usia di atas 50 tahun. Menurut dr. Achmad Yurianto dan dr. Aldiana Halim, sekitar 1,3 juta penduduk Indonesia mengalami kebutaan akibat katarak. Penanganan katarak melalui operasi diharapkan dapat menurunkan prevalensi kebutaan dan meningkatkan perekonomian masyarakat. Hari Penglihatan Sedunia tahun ini mengusung tema 'Mata Sehat Indonesia Maju' dan menekankan pentingnya operasi katarak untuk mengatasi masalah penglihatan di Indonesia.",
                    source = "Kementerian Kesehatan",
                    title = "Katarak Penyebab Terbanyak Kebutaan - sehatNegeriku",
                    url = "https://sehatnegeriku.kemkes.go.id/baca/umum/20201006/4135256/katarak-penyebab-terbanyak-kebutaan/",
                    content = null
                ),

            )
        }
    }
