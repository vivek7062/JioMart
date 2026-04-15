package com.example.jiomartclone.data.local.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(@PrimaryKey(autoGenerate = true) val id :  Int, val name: String, val email: String, val password: String, val otp:Int=0, val createdAt: Long = 0L, val isVerify: Boolean = false)