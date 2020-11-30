package com.example.themovieapp.data.repository

import androidx.room.*
import com.example.themovieapp.data.model.UserEntity
import io.reactivex.Completable

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userEntity: UserEntity): Completable

    @Query("SELECT * FROM user_data WHERE id = :id ")
    fun getUser(id: Int): UserEntity

    @Query("SELECT id from user_data WHERE email = :email and password = :password")
    fun login(email: String, password: String): UserEntity

    @Update
    fun update(userEntity: UserEntity): Int

    @Delete
    fun delete(userEntity: UserEntity)

}