package com.surveasy.surveasy.data.repository

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.surveasy.surveasy.domain.repository.FirebaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebase: Firebase
) : FirebaseRepository {

    override suspend fun loadImage(uri: String, id: Int, imgName: String): String {
        val storage = firebase.storage
        val uri = Uri.parse(uri)
        val storageRef = storage.reference.child(id.toString()).child(imgName)

        return try {
            val task = storageRef.putFile(uri)
            val result = task.await()

            if (result.task.isSuccessful) {
                val downloadUrl = storageRef.downloadUrl.await()
                downloadUrl.toString()
            } else {
                throw Exception("Upload failed: $result")
            }
        } catch (e: Exception) {
            throw e
        }
    }

}