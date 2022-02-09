package com.example.surveasy.list

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.surveasy.databinding.ActivitySurveyproofdialogBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class SurveyProofDialogActivity: AppCompatActivity() {

    val storage = Firebase.storage

    var pickImageFromAlbum = 0
    var uriPhoto : Uri? = null
    private lateinit var binding: ActivitySurveyproofdialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveyproofdialogBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.dialogSendBtn.setOnClickListener {
            uploadStorage(binding.dialogImageview)
        }
        binding.dialogEditBtn.setOnClickListener {
            editPhoto()
        }

        if(checkPermission()){
            var photoPick = Intent(Intent.ACTION_PICK)
            photoPick.type = "image/*"
            startActivityForResult(photoPick,pickImageFromAlbum)

        }else{
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }


    }
    //화면에 사진 나타내기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == pickImageFromAlbum){
            if(resultCode == Activity.RESULT_OK){

                uriPhoto = data?.data
                binding.dialogImageview.setImageURI(uriPhoto)

            }
        }
    }

    private fun checkPermission() : Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }

    //firebase storage upload
    private fun uploadStorage(view: View){
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmm").format(Date())
        val title : String= intent.getStringExtra("title")!!
        val imgName = title+"__"+timestamp
        val storageRef = storage.reference.child(title).child(imgName)

        storageRef.putFile(uriPhoto!!)?.addOnSuccessListener {
            Toast.makeText(this,"제출되었습니다.",Toast.LENGTH_LONG).show()
            finish()
        }


    }
    //사진 다시 선택
    private fun editPhoto(){
        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick,pickImageFromAlbum)
    }




}