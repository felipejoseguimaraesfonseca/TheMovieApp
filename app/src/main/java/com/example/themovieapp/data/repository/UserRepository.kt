package com.example.themovieapp.data.repository

import android.content.Context
import com.example.themovieapp.data.model.UserEntity

class UserRepository(context: Context) {

    private val mDatabase = AppDatabase.getDatabase(context).userDao()

    suspend fun save(user: UserEntity): Boolean? {
        return try {
            val databaseSaveString = mDatabase.save(user).toString()
            val databaseSaveInt = Integer.parseInt(databaseSaveString)
            databaseSaveInt > 0
        } catch (e: NumberFormatException) {
            null
        }
    }

    suspend fun login(email: String, password: String): UserEntity {
        return mDatabase.login(email, password)
    }

    suspend fun update(user: UserEntity): Boolean {
        val mDatabaseInt = mDatabase.update(user).toString().toInt()
        return mDatabaseInt > 0
    }

    suspend fun delete(user: UserEntity): Boolean {
        return mDatabase.delete(user).toString().toBoolean()
    }
}