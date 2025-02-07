package com.surveasy.surveasy.domain.repository

interface FirebaseRepository {

    suspend fun getFbUid(email: String, pw: String): String

    suspend fun loadImage(uri: String, id: Int, imgName: String): String
}