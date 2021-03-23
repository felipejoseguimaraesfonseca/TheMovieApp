package com.example.themovieapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String? = "",

    @ColumnInfo(name = "last_name")
    var lastName: String? = "",

    @ColumnInfo(name = "email")
    var email: String? = "",

    @ColumnInfo(name = "password")
    var password: String? = ""
)
