package com.example.themovieapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings

@Entity(tableName = "user_data")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    var id: Int = 0,

    @ColumnInfo(name = "first_name")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    var firstName: String = "",

    @ColumnInfo(name = "last_name")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    var lastName: String = "",

    @ColumnInfo(name = "email")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    var email: String = "",

    @ColumnInfo(name= "password")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    var password: String = ""
)
