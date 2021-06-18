package com.magednan.jobhunter.repository

import android.content.Context
import android.net.Uri
import com.magednan.jobhunter.firebase.Firebase
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val firebase :Firebase
) {
     fun login(email: String, password: String, context: Context){
        firebase.logIn(email, password, context)
    }
     fun  createNewUser(
        name: String?, job: String?, email: String,
        password: String, context: Context
    ){
        firebase.createNewUser(name, job, email, password,context)
    }
     fun uploadPhoto(uri: Uri, context: Context?): String? {
       return firebase.uploadPhoto(uri, context)
    }

    }