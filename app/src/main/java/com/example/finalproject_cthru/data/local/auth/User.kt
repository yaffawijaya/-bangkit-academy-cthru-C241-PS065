package com.example.finalproject_cthru.data.local.auth

import com.google.firebase.auth.FirebaseUser

data class User(
    val id : String,
    val fullName : String,
    val email : String
)

// Penyesuaian local data ke network data
fun FirebaseUser?.toUser() = this?.let {
    User(
        id = this.uid,
        fullName = this.displayName.orEmpty(),
        email = this.email.orEmpty()
    )
}