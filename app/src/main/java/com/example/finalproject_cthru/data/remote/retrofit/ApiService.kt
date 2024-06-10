package com.example.finalproject_cthru.data.remote.retrofit

import com.example.finalproject_cthru.data.remote.response.ArticleResponse
import com.example.finalproject_cthru.data.remote.response.Response
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

//   API ML Model
    @Multipart
    @POST("/predict/")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response


//   API Article
    @GET("top-headlines")
    fun getNews(
    //        @Query("q") q: String ,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String = "8d319b9609d74c6bb671f9317b130ade"
    ): Call<ArticleResponse>
}