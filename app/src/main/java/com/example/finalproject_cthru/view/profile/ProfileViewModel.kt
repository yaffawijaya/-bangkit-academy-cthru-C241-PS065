package com.example.finalproject_cthru.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.finalproject_cthru.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repo : UserRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun changeProfile(fullName: String) = repo.updateProfile(fullName).asLiveData(Dispatchers.IO)

    fun changePassword() {
        repo.requestChangePasswordByEmail()
    }

    fun getCurrentUser() = repo.getCurrentUser()

    fun isUserLoggedIn() = repo.isLoggedIn()

    fun requestChangePasswordByEmail() = repo.requestChangePasswordByEmail()

    fun doLogout() {
        repo.doLogout()
    }



}