package com.example.myauth

import com.example.myauth.db.User
import com.example.myauth.db.UserDao
import com.example.myauth.retrofit.AuthApiService
import com.example.myauth.retrofit.LoginResponse
import com.example.myauth.retrofit.RegisterRequest
import com.example.myauth.retrofit.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository(private val userDao: UserDao) {

    private val api: AuthApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(AuthApiService::class.java)
    }

    // Check if user exists in Room
    suspend fun getUserFromRoom(): String {
        return withContext(Dispatchers.IO) {
            userDao.getUser(1)
        }
    }

    // Fetch credentials from Retrofit
    suspend fun fetchCredentialsFromApi(email: String, password: String): Response<SignUpResponse> {
        val request = RegisterRequest(email, password)
        return api.SignUp(request)
    }

    // Save user to Room
    suspend fun saveUserToRoom(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    // Fetch profile using Retrofit
    suspend fun fetchProfile(token: String): Response<LoginResponse> {
        return api.logIn("Bearer $token")
    }
}
