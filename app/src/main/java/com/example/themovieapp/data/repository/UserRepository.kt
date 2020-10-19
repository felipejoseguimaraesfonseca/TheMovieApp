package com.example.themovieapp.data.repository

import android.content.Context
import com.example.themovieapp.data.model.UserEntity

class UserRepository(context: Context) {

    private val mDatabase = AppDatabase.getDatabase(context).userDao()

    fun get(id: Int, email: String, password: String): UserEntity {
        return mDatabase.load(id, email, password)
    }

    fun save(user: UserEntity): Boolean {
        return mDatabase.save(user) > 0
    }

    fun update(user: UserEntity): Boolean {
        return mDatabase.update(user) > 0
    }
    
    fun delete(user: UserEntity) {
        mDatabase.delete(user)
    }

}