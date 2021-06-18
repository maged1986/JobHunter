package com.magednan.jobhunter.viewmodels

import android.content.Context
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.magednan.jobhunter.repository.AuthRepository

class SignUpViewModel @ViewModelInject constructor(
    val authRepository: AuthRepository
) : ViewModel() {

     fun  createNewUser(
        name: String?, job: String?,email: String,
        password: String, context: Context
    ){
        authRepository.createNewUser(name, job,  email, password, context)
    }
     fun uploadPhoto(uri: Uri, context: Context?): String? {
        return authRepository.uploadPhoto(uri, context)
    }

}