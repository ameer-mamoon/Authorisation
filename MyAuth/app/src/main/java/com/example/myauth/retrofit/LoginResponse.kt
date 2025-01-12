package com.example.myauth.retrofit

data class LoginResponse(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val role: String,
    val avatar: String,
    val createdAt:String,
    val updatedAt: String
)
