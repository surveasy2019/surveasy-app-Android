package com.surveasy.surveasy.my.history.detail

import android.Manifest
import android.app.Activity
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
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.surveasy.surveasy.databinding.ActivityMyViewUpdatePhotoBinding
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

class MyViewUpdatePhotoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyViewUpdatePhotoBinding
    val db = Firebase.firestore
    val storage = Firebase.storage
    var pickImageFromAlbum = 0
    var uriPhoto: Uri? = null
    val PERMISSION_CODE = 101
    val REQUIRED_PERMISSION = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyViewUpdatePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //val filePath = intent.getStringExtra("filePath")
        //val filePath = intent.getStringExtra("filePath").toString()
        val id = intent.getIntExtra("id",0)

        // 앨범으로
        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick, pickImageFromAlbum)


        binding.historyUpdateSendBtn.setOnClickListener {
            uploadStorage()

        }
        binding.historyUpdateEditBtn.setOnClickListener {
            editPhoto()
        }
    }



    //화면에 사진 나타내기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //binding.historyTextUpdate.text = "새로 첨부할 이미지"
        if(requestCode == pickImageFromAlbum){
            if(resultCode == Activity.RESULT_OK){
                uriPhoto = data?.data
                binding.historyUpdateImage.setImageURI(uriPhoto)

            }
        }
    }

    private fun editPhoto(){
        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick,pickImageFromAlbum)
    }

    //firebase storage upload
    private fun uploadStorage(){
        val file = intent.getStringExtra("filePath").toString()
//        val file = "surveytips2image(2).jpeg"
        val id: Int = intent.getIntExtra("id",0)!!
        val filePath = storage.reference.child(id.toString()).child(file.toString())
        val idChecked = intent.getIntExtra("idChecked",0)!!
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmm").format(Date())
        val imgName = Firebase.auth.currentUser!!.uid+"__"+timestamp

        val storageRef = storage.reference.child(id.toString()).child(imgName)
        //null 방지
        if(uriPhoto==null){
            Toast.makeText(this, "사진을 선택하세요", Toast.LENGTH_LONG).show()
        }else{
            val uploadTask = storageRef.putFile(uriPhoto!!)
            uploadTask.addOnSuccessListener {
                updateFilePath(idChecked,imgName)
                val intent = Intent(this, MyViewHistoryUpdateFinDialogActivity::class.java)
                startActivity(intent)
                finish()
                deletePhoto(filePath)
                Log.d(TAG, "uploadStorage: success delete")

            }
            binding.historyUpdateSendBtn.visibility = View.INVISIBLE
            Toast.makeText(this,"완료 화면을 변경 중입니다.", Toast.LENGTH_LONG).show()
        }



    }

    // 이전 사진 삭제하기
    private fun deletePhoto(storageRef : StorageReference){
        storageRef.delete().addOnSuccessListener {
            Log.d(TAG, "deletePhoto: delete 완료")

        }
    }

    private fun updateFilePath(id : Int, imgName : String){
        val uid = Firebase.auth.currentUser!!.uid
        val dbRef = db.collection("panelData").document(uid.toString())
            .collection("UserSurveyList").document(id.toString())
        dbRef.update(
            "filePath",
            imgName.toString()
        )
            .addOnSuccessListener { result ->
                Log.d(ContentValues.TAG, "##### filePath update 성공")
            }
    }


}