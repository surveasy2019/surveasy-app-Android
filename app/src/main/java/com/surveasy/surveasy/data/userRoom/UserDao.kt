package com.surveasy.surveasy.data.userRoom

import androidx.room.*
import com.surveasy.surveasy.my.notice.noticeRoom.Notice

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(vararg users: User)

    @Query("UPDATE UserTable SET fcmToken = :FcmToken WHERE uid = :Uid")
    fun updateFcm(Uid : String, FcmToken : String)

    //@Query("UPDATE UserTable SET firstScreen = :FS WHERE uid = :Uid")
    //fun updateShowFS(Uid : String, FS : Boolean)

    @Delete
    fun delete(vararg users: User)

    @Query("DELETE FROM UserTable")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM UserTable WHERE uid = :Uid")
    fun getNumUid(Uid : String) : Int

    @Query("SELECT COUNT(*) FROM UserTable")
    fun getNumAll() : Int

    @Query("SELECT uid FROM UserTable")
    fun getUid() : String

    //@Query("SELECT firstScreen FROM UserTable")
    //fun getFS() : Boolean?

    @Query("SELECT birthYear FROM UserTable")
    fun getBirth() : Int

    @Query("SELECT gender FROM UserTable")
    fun getGender() : String

    @Query("SELECT fcmToken FROM UserTable")
    fun getFcmToken() : String

    @Query("SELECT autoLogin FROM UserTable")
    fun getAutoLogin() : Boolean

    @Query("SELECT * FROM UserTable")
    fun getAll(): Array<User>


}