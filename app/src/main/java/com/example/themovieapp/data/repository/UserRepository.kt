package com.example.themovieapp.data.repository

import android.content.Context
import com.example.themovieapp.data.model.UserEntity

class UserRepository(context: Context) {

    private val mDatabase = AppDatabase.getDatabase(context).userDao()

     suspend fun getUser(id: Int): UserEntity {
        return mDatabase.getUser(id)
    }

    suspend fun login(email: String, password: String = ""): UserEntity {
        return mDatabase.login(email, password)
    }

    suspend fun save(user: UserEntity): Boolean {
        val mDatabaseInt = mDatabase.save(user).toString().toInt()
        return mDatabaseInt > 0
    }

    suspend fun update(user: UserEntity): Boolean {
        val mDatabaseInt = mDatabase.update(user).toString().toInt()
        return mDatabaseInt > 0
    }

    suspend fun delete(user: UserEntity) {
        mDatabase.delete(user)
    }

}