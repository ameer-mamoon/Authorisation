package com.example.myauth.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myauth.db.User
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel(){

    private val loginResponse = MutableLiveData<LoginResponse>()
    private val signupResponse= MutableLiveData<SignUpResponse>()
    private val error = MutableLiveData<String>()

    //public read only
    val loginResponse_: LiveData<LoginResponse> get() = loginResponse
    val signupResponse_: LiveData<SignUpResponse> get() = signupResponse
    val error_: LiveData<String> get() = error

    fun login(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(token)
                if (response.isSuccessful) {
                    loginResponse.postValue(response.body())
                } else {
                    error.postValue("Login failed: ${response.body()}")
                }
            } catch (e: Exception) {
                error.postValue("Error: ${e.message}")
            }
        }
    }

    fun signup(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = repository.signup(registerRequest)
                if (response.isSuccessful) {
                    signupResponse.postValue(response.body())
                } else {
                    error.postValue("Signup failed: ${response.message()}")
                }
            } catch (e: Exception) {
                error.postValue("Error: ${e.message}")
            }
        }
    }

}