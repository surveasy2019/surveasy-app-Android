package com.surveasy.surveasy.userRoom

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}