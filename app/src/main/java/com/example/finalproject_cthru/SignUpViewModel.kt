package com.example.finalproject_cthru

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.finalproject_cthru.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {
    fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ) = repository.doRegister(
        email,
        fullName,
        password,
    ).asLiveData(
        Dispatchers.IO,
    )
}