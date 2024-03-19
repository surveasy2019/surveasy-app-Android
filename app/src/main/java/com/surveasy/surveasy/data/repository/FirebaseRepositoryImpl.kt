package com.surveasy.surveasy.data.repository

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.google.firebase.storage.storage
import com.surveasy.surveasy.domain.repository.FirebaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebase: Firebase
) : FirebaseRepository {
    override suspend fun getFbUid(email: String, pw: String): String {
        return try {
            val task = firebaseAuth.signInWithEmailAndPassword(email, pw)
            task.await()
            if (task.isSuccessful) {
                firebaseAuth.uid.toString()
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

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
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    override suspend fun checkVersion(version: String): Boolean {


        return try {

            val remoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
            remoteConfig.setConfigSettingsAsync(configSettings)

            val defaultConfigMap = mapOf(
                REMOTE_KEY to "0.0.0"
            )
            remoteConfig.setDefaultsAsync(defaultConfigMap)

            val task = remoteConfig.fetchAndActivate()
            task.await()
            if (task.isSuccessful) {
                val targetVersion = remoteConfig.getString(REMOTE_KEY)
                targetVersion == version
            } else {
                true
            }
        } catch (e: Exception) {
            true
        }
    }

    companion object {
        const val REMOTE_KEY = "version"
    }

}