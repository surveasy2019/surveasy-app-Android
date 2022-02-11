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
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.surveasy.databinding.ActivitySurveyproofdialogBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SurveyProofDialogActivity: AppCompatActivity() {

    val storage = Firebase.storage
    val db = Firebase.firestore

    val thisSurveyInfo = ArrayList<UserSurveyItem>()

    var pickImageFromAlbum = 0
    var uriPhoto: Uri? = null
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
        val title: String = intent.getStringExtra("title")!!

        //설문 정보 가져와서 저장해두기
        db.collection("AppTest1").document(title)
            .get().addOnSuccessListener { result ->

                val item: UserSurveyItem = UserSurveyItem(
                    500,
                    result["name"] as String,
                    result["recommend"] as String,
                    false
                )

                thisSurveyInfo.add(item)
                Toast.makeText(this,"*******저장 성공 ${thisSurveyInfo.get(0).id}",Toast.LENGTH_LONG).show()


            }.addOnFailureListener {
                Toast.makeText(this,"*******저장 실패 ${thisSurveyInfo.toString()}",Toast.LENGTH_LONG).show()
            }

        //permission 있으면 앨범에 들어가게 되어있음
        if (checkPermission()) {
            var photoPick = Intent(Intent.ACTION_PICK)
            photoPick.type = "image/*"
            startActivityForResult(photoPick, pickImageFromAlbum)

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }


    }
    //참여한 설문 리스트 firestore에 업데이트
    private fun updateList(){
        val list = hashMapOf(
            "reward" to thisSurveyInfo.get(0).reward,
            "id" to thisSurveyInfo.get(0).id,
            "responseDate" to thisSurveyInfo.get(0).responseDate,
            "isSent" to thisSurveyInfo.get(0).isSent

        )
        val title: String = intent.getStringExtra("title")!!

        if (Firebase.auth.currentUser!!.uid != null) {
            db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                .collection("UserSurveyList").document(title)
                .set(list).addOnSuccessListener {
                    Toast.makeText(this,"#####info save success", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this,"#####failed", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this,"#####user null", Toast.LENGTH_LONG).show()
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
        val docRef = db.collection("AndroidUser").document("gOyfH6eGm7cL24zZn1346iWMu6D3")
            .collection("UserSurveyList").document(title)
        val uploadTask = storageRef.putFile(uriPhoto!!)

        uploadTask.addOnSuccessListener {
            Toast.makeText(this,"업로드 하는 중",Toast.LENGTH_SHORT).show()
            updateList()
            val intent = Intent(this,SurveyProofLastDialogActivity::class.java)
            //val list = intent.putExtra("list",thisSurveyInfo)
            startActivity(intent)


        }

    }
    //사진 다시 선택
    private fun editPhoto(){
        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick,pickImageFromAlbum)
    }





    }






