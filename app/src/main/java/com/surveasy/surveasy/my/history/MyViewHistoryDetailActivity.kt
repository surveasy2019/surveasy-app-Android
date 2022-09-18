package com.surveasy.surveasy.my.history

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityMyViewHistoryDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import kotlin.properties.Delegates

class MyViewHistoryDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyViewHistoryDetailBinding
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

        //activity 들어가자마자 permission 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show()
        }else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, PERMISSION_CODE)
        }

        val id : Int = intent.getIntExtra("id",0)
        val lastIdCheck : Int = intent.getIntExtra("idChecked",0)
        val filePath : String? = intent.getStringExtra("filePath")
        val title : String = intent.getStringExtra("title")!!
        val date : String = intent.getStringExtra("date")!!
        val reward : Int = intent.getIntExtra("reward",0)

        binding.historyDetailTitle.text = title
        binding.historyDetailReward.text = reward.toString()+"원"
        binding.historyDetailDate.text = "참여일자 : $date"
        fetchLastImg(id, filePath)
        CoroutineScope(Dispatchers.Main).launch {
            fetchProgress(id)

            binding.historyDetailUploadBtn.setOnClickListener{
                //permission 없는 상태로 upload 버튼 누르면 설정으로 이동 유도하는 창
                if(surveyProgress==2){
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

    // 기존에 첨부한 이미지 보여주기
    private fun fetchLastImg(id : Int, filePath : String?) {


//        val storageRef: StorageReference = storage.reference.child("historyTest")
        val storageRef: StorageReference = storage.reference.child(id.toString())
//        val file1: StorageReference = storageRef.child("surveytips2image(3).png")
        val file1: StorageReference = storageRef.child(filePath.toString())

        Glide.with(this).load(R.raw.app_loading).into(binding.historyDetailLastCapture)

        file1.downloadUrl.addOnSuccessListener { item ->
            Log.d(TAG, "fetchLastImg: $item")
            Glide.with(this).load(item).into(binding.historyDetailLastCapture)
        }
    }

    private suspend fun fetchProgress(id : Int){
        withContext(Dispatchers.IO){
            db.collection("surveyData").document(id.toString()).get()
                .addOnSuccessListener {
                        document ->
                    if(id==0){
                        surveyProgress = 3
                    }else{
                        surveyProgress = Integer.parseInt(document["progress"].toString())
                    }
                }
            Log.d(TAG, "fetchProgress: $surveyProgress")
        }

    }


}