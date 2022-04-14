const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.androidPushNotification = functions.firestore.document("NotificationData/{docId}")
    .onCreate((snapshot, context) => {

        admin.messaging().sendToTopic(
                    "all" ,
                    {
                        notification: {
                            title: snapshot.data().title,
                            body: snapshot.data().body,
                            click_action: "FCM_EXE_ACTIVITY"
                        }
                    }

                )
            })

exports.androidWrongPushNotification = functions.firestore.document("ProofCancel/{docId}")
    .onCreate((snapshot, context) => {

        admin.messaging().sendToTopic(
                    "cancelAlert",
                    {
                        notification: {
                            title: snapshot.data().title,
                            body: snapshot.data().body
                        }
                    }

                )
            })











