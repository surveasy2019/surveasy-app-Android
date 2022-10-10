package com.surveasy.surveasy.my.history

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityMyViewHistoryDetailBinding
import com.surveasy.surveasy.home.HomeFragment
import kotlinx.coroutines.*

class MyViewHistoryDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyViewHistoryDetailBinding
    val model by viewModels<MyViewHistoryDetailViewModel>()
    val db = Firebase.firestore
    val storage = Firebase.storage
    var surveyProgress = 0
    val PERMISSION_CODE = 101
    var pickImageFromAlbum = 0
    val REQUIRED_PERMISSION = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyViewHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.filePath.clear()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.historyDetailFragment, MyViewHistoryDetailFragment()).commit()

        //activity 들어가자마자 permission 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show()
        }else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, PERMISSION_CODE)
        }

        val id : Int = intent.getIntExtra("id",0)
        val lastIdCheck : Int = intent.getIntExtra("idChecked",0)
        //val filePath : String? = intent.getStringExtra("filePath")
        val title : String = intent.getStringExtra("title")!!
        val date : String = intent.getStringExtra("date")!!
        val reward : Int = intent.getIntExtra("reward",0)

        val item = MyViewDetailItem(id, lastIdCheck, null, date, title, reward)


        CoroutineScope(Dispatchers.Main).launch {
            fetchProgress(id)
            fetchFilePath(lastIdCheck)
            uploadModel(item)
        }

        // ToolBar
        setSupportActionBar(binding.ToolbarHistoryDetail)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarHistoryDetail.setNavigationOnClickListener { onBackPressed() }



    }

    fun activityFinish(){
        finish()
    }



    @DelicateCoroutinesApi
    private suspend fun fetchProgress(id : Int){
        withContext(Dispatchers.IO){
            db.collection("surveyData").document(id.toString()).get()
                .addOnSuccessListener { document ->
                    if (id == 0) {
                        model.progress.add(MyViewDetailProgress(3))
                        //Log.d(TAG, "fetchProgress: progress 0 일때")
                    } else {
                        model.progress.add(MyViewDetailProgress(Integer.parseInt(document["progress"].toString())))
                        //Log.d(TAG, "fetchProgress: ${Integer.parseInt(document["progress"].toString())}")
                    }
                }

        }
    }

    private suspend fun fetchFilePath(lastId : Int){
        val uid = Firebase.auth.currentUser!!.uid
        withContext(Dispatchers.IO){
            db.collection("panelData").document(uid.toString())
                .collection("UserSurveyList").document(lastId.toString()).get()
                .addOnSuccessListener { document ->
                    model.filePath.add(MyViewFilePath(document["filePath"].toString()))

                }
        }

    }

    private suspend fun uploadModel(item : MyViewDetailItem){
        model.detailModel.add(item)

    }
}