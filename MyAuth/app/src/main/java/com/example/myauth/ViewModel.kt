package com.example.myauth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myauth.db.User
import com.example.myauth.retrofit.LoginResponse
import com.example.myauth.retrofit.SignUpResponse
import kotlinx.coroutines.launch

class ViewModel(private val repository: Repository) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    private val _signupResponse = MutableLiveData<SignUpResponse>()
    val signupResponse: LiveData<SignUpResponse> get() = _signupResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun authenticate(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Check Room for existing user credentials
                val user = repository.getUserFromRoom()
                if (user != null) {
                    Log.d("Result: Auth", "User found in Room: ${user}")
                    _loginResponse.postValue(LoginResponse(0,"abc@mail.com","abc",user,"abc","abc","abc","abc"))
                } else
                {
                    // No credentials in Room, hit API
                    val response = repository.fetchCredentialsFromApi(email, password)
                    if (response.isSuccessful) {
                        val signupResponse = response.body()
                        if (signupResponse != null) {
                            // Save fetched credentials to Room
                            val newUser = User(0, email, password)
                            repository.saveUserToRoom(newUser)
                            _signupResponse.postValue(response.body())
                        }
                    } else {
                        _error.postValue("Login failed: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }

    fun fetchProfile(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchProfile(token)
                if (response.isSuccessful) {
                    _loginResponse.postValue(response.body())
                } else {
                    _error.postValue("Failed to fetch profile: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }
}
