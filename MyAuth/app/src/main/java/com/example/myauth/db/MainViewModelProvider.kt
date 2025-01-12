package com.example.authorisation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myauth.db.MainViewModel
import com.example.myauth.db.UserRepository

class MainViewModelProvider(private val repository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(repository) as T

    }

}