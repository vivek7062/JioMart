package com.example.jiomartclone.data.local.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE email=:email")
    suspend fun getUser(email: String): UserEntity?


    @Query("UPDATE UserEntity SET otp=:otp where email=:email")
    suspend fun updateOTP(email: String, otp:Int)

    @Query("UPDATE UserEntity SET isVerify=:verified where email=:email")
    suspend fun getVerifiedUser(email: String, verified: Boolean)

}