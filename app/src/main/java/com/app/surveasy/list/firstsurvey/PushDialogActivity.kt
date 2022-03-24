package com.app.surveasy.list.firstsurvey

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.surveasy.R
import com.app.surveasy.databinding.ActivityPushdialogBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.zip.Inflater

class PushDialogActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPushdialogBinding
    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPushdialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PushNoticeOFFBtn.setOnClickListener{
            db.collection("AndroidUser").document(uid)
                .update("pushOn", false)
            finish()
        }

        binding.PushNoticeONBtn.setOnClickListener{
            db.collection("AndroidUser").document(uid)
                .update("pushOn", true)
            finish()
        }


    }
}