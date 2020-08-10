package com.example.themovieapp.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userEntity: UserEntity)

    @Query("SELECT * FROM user_data WHERE id = :id")
    fun getUser(id: Int): UserEntity

    @Query("SELECT * FROM user_data WHERE email = :email and password = :password")
    fun login(email: String, password: String): Int

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

}