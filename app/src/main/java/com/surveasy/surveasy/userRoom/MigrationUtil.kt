package com.surveasy.surveasy.userRoom

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 : Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE UserTable ADD COLUMN testRoom INTEGER NOT NULL DEFAULT 0")
    }
}