package com.example.themovieapp.data.repository

import androidx.room.*
import com.example.themovieapp.data.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userEntity: UserEntity): Long

    @Query("SELECT * FROM user_data WHERE id = :id")
    fun load(id: Int): UserEntity

    @Update
    fun update(userEntity: UserEntity): Int

    @Delete
    fun delete(userEntity: UserEntity)

}