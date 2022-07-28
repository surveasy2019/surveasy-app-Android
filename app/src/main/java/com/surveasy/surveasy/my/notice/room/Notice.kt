package com.surveasy.surveasy.my.notice.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoticeTable")
data class Notice(
    @PrimaryKey val id: Int,
    var isOpened: Boolean?
) {
}