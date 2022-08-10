package com.surveasy.surveasy.my.history

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityHomeOpinionAnswerBinding
import com.surveasy.surveasy.databinding.ActivityMyViewUpdatePhotoBinding
import com.surveasy.surveasy.list.SurveyProofLastDialogActivity

class MyViewUpdatePhotoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyViewUpdatePhotoBinding

    val storage = Firebase.storage
    var pickImageFromAlbum = 0
    var uriPhoto: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyViewUpdatePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val filePath = intent.getStringExtra("filePath")
        val filePath = intent.getStringExtra("filePath").toString()
        val id = intent.getIntExtra("id",0)
        fetchAnswerImg(id, filePath)

        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick, pickImageFromAlbum)
    }

    // 기존에 첨부한 이미지 보여주기
    private fun fetchAnswerImg(id : Int, filePath : String) {


//        val storageRef: StorageReference = storage.reference.child("historyTest")
        val storageRef: StorageReference = storage.reference.child(id.toString())
//        val file1: StorageReference = storageRef.child("surveytips2image(1).jpeg")
        val file1: StorageReference = storageRef.child(filePath.toString())

        Glide.with(this).load(R.raw.app_loading).into(binding.MyViewHistoryLastPhoto)

        file1.downloadUrl.addOnSuccessListener { item ->
            Glide.with(this).load(item).into(binding.MyViewHistoryLastPhoto)
        }
    }

    //화면에 사진 나타내기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == pickImageFromAlbum){
            if(resultCode == Activity.RESULT_OK){
                uriPhoto = data?.data
                binding.MyViewHistoryLastPhoto.setImageURI(uriPhoto)

            }
        }
    }
    //사진 다시 선택
    private fun editPhoto(){
        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick,pickImageFromAlbum)
    }

//    //firebase storage upload
//    private fun uploadStorage(view: View){
//
//        val id: Int = intent.getIntExtra("id",0)!!
//        val idChecked: Int = intent.getIntExtra("idChecked",0)!!
//        val imgName = Firebase.auth.currentUser!!.uid
////        val storageRef = storage.reference.child(idChecked.toString()).child(imgName)
//        val storageRef = storage.reference.child(id.toString()).child(imgName)
//        val uploadTask = storageRef.putFile(uriPhoto!!)
//
//        uploadTask.addOnSuccessListener {
//            //Toast.makeText(this,"업로드 하는 중",Toast.LENGTH_SHORT).show()
//            updateList()
//            val intent = Intent(this, SurveyProofLastDialogActivity::class.java)
//            intent.putExtra("reward",thisSurveyInfo.get(0).reward)
//            intent.putExtra("title", thisSurveyInfo.get(0).title)
//            intent.putExtra("idChecked",idChecked)
//            intent.putExtra("id",id)
//            startActivity(intent)
//        }
//
//    }

    // 다시 첨부하기
    private fun updateImg(){

    }
}