package com.example.finalproject_cthru.data.remote.retrofit

import com.example.finalproject_cthru.data.remote.response.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/predict/")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response
}