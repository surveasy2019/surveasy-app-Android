package com.surveasy.surveasy.my.notice.room

import androidx.room.*

@Dao
interface NoticeDao {

    // Insert
    @Insert
    fun insert(notice : Notice)

    @Insert
    fun insertAll(vararg notices: Notice)


    // Update
    @Update
    fun update(vararg notices: Notice)


    // Delete
    @Delete
    fun delete(vararg notices: Notice)


    // Query
    @Query("SELECT COUNT(*) FROM NoticeTable")
    fun getNum() : Int

    @Query("SELECT id FROM NoticeTable ORDER BY id DESC LIMIT 1")
    fun getLastID() : Int

    @Query("SELECT * FROM NoticeTable")
    fun getAll(): Array<Notice>

    @Query("SELECT id FROM NoticeTable WHERE isOpened = 0")
    fun getNotOpened() : Array<Notice>

    @Query("SELECT * FROM NoticeTable WHERE isOpened = 1")
    fun getOpened() : Array<Notice>

    @Query("DELETE FROM NoticeTable")
    fun deleteAll()



}