package com.example.themovieapp.data.repository

import androidx.room.*
import com.example.themovieapp.data.model.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(userEntity: UserEntity)

    @Query("SELECT * FROM user_data WHERE id = :id ")
    suspend fun getUser(id: Int): UserEntity

    @Query("SELECT id from user_data WHERE email = :email and password = :password")
    suspend fun login(email: String, password: String): UserEntity

    @Update
    suspend fun update(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

}