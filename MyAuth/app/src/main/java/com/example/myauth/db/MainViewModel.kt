package com.example.myauth.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myauth.db.User
import com.example.myauth.db.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(val repository: UserRepository) :  ViewModel() {

     fun insertUser(user: User)
    {
        GlobalScope.launch {
            repository.insertUser(user)
        }
    }

    suspend fun getUserById(id:Int):String {

            return repository.getUserById(id)

    }

}