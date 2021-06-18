package com.magednan.jobhunter.viewmodels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.magednan.jobhunter.repository.AuthRepository

class SignInViewModel @ViewModelInject constructor(
    val authRepository: AuthRepository
): ViewModel() {
     fun login(email: String, password: String, context: Context){
        authRepository.login(email, password, context)
    }


}