package com.example.themovieapp.data.repository

import androidx.room.*
import com.example.themovieapp.data.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userEntity: UserEntity): Long

    @Query("SELECT * FROM user_data WHERE id = :id and email = :email and password = :password")
    fun load(id: Int, email: String, password: String): UserEntity

    @Update
    fun update(userEntity: UserEntity): Int

    @Delete
    fun delete(userEntity: UserEntity)

}