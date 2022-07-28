package com.surveasy.surveasy.my.notice.room

import androidx.room.*

@Dao
interface NoticeDao {
    @Insert
    fun insertAll(vararg notices: Notice)

    @Update
    fun update(vararg notices: Notice)

    @Delete
    fun delete(vararg notices: Notice)

    @Query("SELECT * FROM NoticeTable")
    fun getAll(): Array<Notice>

    @Query("SELECT * FROM NoticeTable WHERE isOpened = 0")
    fun getNotOpened() : Array<Notice>

    @Query("SELECT * FROM NoticeTable WHERE isOpened = 1")
    fun getOpened() : Array<Notice>


}