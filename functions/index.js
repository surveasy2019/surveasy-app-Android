const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.androidPushNotification = functions.firestore.document("NotificationData/{docId}")
    .onCreate((snapshot, context) => {
        admin.messaging().sendToTopic(
            "weather",
            {
                notification: {
                    title: snapshot.data().title,
                    body: snapshot.data().body
                }
            }
        )
    })