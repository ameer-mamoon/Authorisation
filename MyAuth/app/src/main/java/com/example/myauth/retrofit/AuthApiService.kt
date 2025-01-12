package com.example.myauth.retrofit

import com.example.myauth.db.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @GET("profile")
    suspend fun logIn(@Header("Authorization") token: String): Response<LoginResponse>

    //Actually for Signup/Register
    @POST("login")
    suspend fun SignUp(@Body registerRequest: RegisterRequest):Response<SignUpResponse>

}