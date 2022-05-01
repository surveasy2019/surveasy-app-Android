package com.android.surveasy.list.firstsurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.surveasy.databinding.ActivityPushdialogBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PushDialogActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPushdialogBinding
    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPushdialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PushNoticeOFFBtn.setOnClickListener{
            db.collection("panelData").document(uid)
                .update("pushOn", false)
            finish()
        }

        binding.PushNoticeONBtn.setOnClickListener{
            db.collection("panelData").document(uid)
                .update("pushOn", true)
            finish()
        }


    }
}