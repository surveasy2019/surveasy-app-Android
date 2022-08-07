package com.surveasy.surveasy.userRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey val uid : String,
    val birthYear : Int,
    val gender: String,
    val fcmToken: String,
    val autoLogin: Boolean
) {
}