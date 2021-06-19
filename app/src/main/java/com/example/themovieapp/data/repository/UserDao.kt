package com.example.themovieapp.data.repository

import androidx.room.*
import com.example.themovieapp.data.model.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun save(userEntity: UserEntity)

    @Query("SELECT * from user_data WHERE email = :email and password = :password")
    suspend fun login(email: String, password: String): UserEntity

    @Update
    suspend fun update(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

}