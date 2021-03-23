package com.example.themovieapp.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.themovieapp.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private lateinit var INSTANCE: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(AppDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}