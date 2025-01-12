package com.example.myauth.retrofit

import android.util.Log
import com.example.myauth.db.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository  {

    private val api:AuthApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
            Log.d("Result",level.toString())
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        Log.d("Result",client.toString())


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(AuthApiService::class.java)
    }

    suspend fun login(token: String): Response<LoginResponse> {
        return api.logIn(token)
    }

    suspend fun signup(registerRequest: RegisterRequest): Response<SignUpResponse> {
        return api.SignUp(registerRequest)
    }

}