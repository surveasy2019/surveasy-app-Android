package com.beta.surveasy.fcm

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.beta.surveasy.MainActivity
import com.beta.surveasy.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.beta.surveasy.FCM_SplashActivity
import com.beta.surveasy.SplashActivity

const val channelId = "notification_channel"
const val channelName = "com.example.surveasy"

class MyFirebaseMessagingService :  FirebaseMessagingService() {

    // Generate the notification
    // Attach the notification created with the custom layout
    // Show the notification

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        // sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.getNotification() != null) {
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }
    }

//    @SuppressLint
//    fun getRemoteView(title: String, message: String) : RemoteViews {
//        val remoteView = RemoteViews("com.example.surveasy", R.layout.notification)
//
//        remoteView.setTextViewText(R.id.notification_title, title)
//        remoteView.setTextViewText(R.id.notification_message, message)
//        remoteView.setImageViewResource(R.id.notification_checkimg, R.drawable.checkimg)
//
//        return remoteView
//    }

    fun generateNotification(title: String, message: String) {
        val push_default_list = true
        val intent = Intent(this, FCM_SplashActivity::class.java)
            intent.putExtra("push_default_list", push_default_list)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        Toast.makeText(baseContext, "***** $push_default_list", Toast.LENGTH_SHORT).show()


//        수정하면 되는 부분
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // channel id, channel name
        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.checkimg)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)



        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())

    }

}