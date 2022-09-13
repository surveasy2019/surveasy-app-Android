package com.surveasy.surveasy.my.history

import android.content.ContentValues.TAG
import android.content.Intent
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
    var surveyProgress = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyViewHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id : String = intent.getStringExtra("id")!!
        val lastIdCheck : Int = intent.getIntExtra("idChecked",0)
        //val filePath : String = intent.getStringExtra("filePath")!!
        CoroutineScope(Dispatchers.Main).launch {
            fetchProgress(id)

            binding.MyViewHistoryReuploadBtn.setOnClickListener {
                if(surveyProgress>=3){
                    Toast.makeText(applicationContext,"progress 2", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@MyViewHistoryDetailActivity, MyViewUpdatePhotoActivity::class.java)
                    //intent.putExtra("filePath", filePath)
                    //storage 폴더 접근 위해
                    intent.putExtra("id", id)
                    intent.putExtra("idChecked", lastIdCheck)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext,"progress 3", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    private suspend fun fetchProgress(id : String){
        withContext(Dispatchers.IO){
            db.collection("surveyData").document(id.toString()).get()
                .addOnSuccessListener {
                        document ->
                    if(id.equals("0")){
                        surveyProgress = 3
                    }else{
                        surveyProgress = Integer.parseInt(document["progress"].toString())
                    }
                }
            Log.d(TAG, "fetchProgress: $surveyProgress")
        }

    }
}