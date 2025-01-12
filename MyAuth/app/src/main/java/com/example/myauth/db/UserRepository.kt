package com.example.myauth.db

import androidx.lifecycle.LiveData

class UserRepository(private val dao:UserDao) {

    suspend fun insertUser(user: User) = dao.insert(user)

    suspend fun getUserById(id:Int): String
    {
        return dao.getUser(id)
    }

}