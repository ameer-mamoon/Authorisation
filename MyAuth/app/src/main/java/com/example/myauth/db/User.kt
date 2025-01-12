package com.example.myauth.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey(autoGenerate = true) val id :Int,
    val name:String,
    val password:String)
