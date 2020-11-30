package com.example.themovieapp.data.repository

import android.content.Context
import com.example.themovieapp.data.model.UserEntity

class UserRepository(context: Context) {

    private val mDatabase = AppDatabase.getDatabase(context).userDao()

    fun getUser(id: Int): UserEntity {
        return mDatabase.getUser(id)
    }

    fun login(email: String, password: String): UserEntity {
        return mDatabase.login(email, password)
    }

    fun save(user: UserEntity): Boolean {
        return mDatabase.save(user).toString().toInt() > 0
    }

    fun update(user: UserEntity): Boolean {
        return mDatabase.update(user) > 0
    }
    
    fun delete(user: UserEntity) {
        mDatabase.delete(user)
    }

}