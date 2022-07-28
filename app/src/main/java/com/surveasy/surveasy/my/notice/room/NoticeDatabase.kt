package com.surveasy.surveasy.my.notice.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notice::class], version = 1)
abstract class NoticeDatabase : RoomDatabase() {
    abstract fun noticeDao() : NoticeDao
}