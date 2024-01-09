package com.surveasy.surveasy.domain.repository

interface FirebaseRepository {
    suspend fun loadImage(uri: String, id: Int, imgName: String): String
}