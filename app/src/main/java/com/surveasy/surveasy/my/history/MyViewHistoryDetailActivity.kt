package com.surveasy.surveasy.my.history

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityMyViewHistoryDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class MyViewHistoryDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyViewHistoryDetailBinding
    val db = Firebase.firestore
    var surveyProgress : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyViewHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id : Int = intent.getIntExtra("id", 0)
        CoroutineScope(Dispatchers.Main).launch {
            fetchProgress(id)

            binding.MyViewHistoryReuploadBtn.setOnClickListener {
                if(surveyProgress==2){
                    Toast.makeText(applicationContext,"progress 2", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,"progress 3", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    private suspend fun fetchProgress(id : Int){
        withContext(Dispatchers.IO){
            db.collection("surveyData").document(id.toString()).get()
                .addOnSuccessListener {
                        document ->
                    surveyProgress = Integer.parseInt(document["progress"].toString()) as Int
                }
            Log.d(TAG, "fetchProgress: $surveyProgress")
        }

    }
}