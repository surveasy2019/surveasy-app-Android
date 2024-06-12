package com.surveasy.surveasy.app

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken: $token")
    }

}