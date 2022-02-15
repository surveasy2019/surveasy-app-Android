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
import java.lang.reflect.Array.get
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SurveyProofDialogActivity: AppCompatActivity() {

    val storage = Firebase.storage
    val db = Firebase.firestore

    //이 Activity 안에서만 쓰이는 임시 list
    val thisSurveyInfo = ArrayList<UserSurveyItem>()

    var pickImageFromAlbum = 0
    var uriPhoto: Uri? = null

    private lateinit var binding: ActivitySurveyproofdialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveyproofdialogBinding.inflate(layoutInflater)
        val id :String = intent.getStringExtra("id")!!

        setContentView(binding.root)

        //설문 정보 가져와서 저장해두기
        db.collection("AndroidSurvey").document(id)
            .get().addOnSuccessListener { document ->

                val item: UserSurveyItem = UserSurveyItem(
                    document["id"] as String,
                    document["title"] as String?,
                    500,
                    document["uploadDate"] as String?,
                    false
                )
                thisSurveyInfo.add(item)

            }.addOnFailureListener {
                Toast.makeText(this,"*******저장 실패 ${thisSurveyInfo.toString()}",Toast.LENGTH_LONG).show()
            }



        binding.dialogSendBtn.setOnClickListener {
            uploadStorage(binding.dialogImageview)
        }

        binding.dialogEditBtn.setOnClickListener {
            editPhoto()
        }

        //Toast.makeText(this,"###${id}",Toast.LENGTH_LONG).show()


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

        // AndroidUser-UserSurveyList에 참여 설문 추가
        val id: String = intent.getStringExtra("id")!!
        val list = hashMapOf(
            "id" to thisSurveyInfo.get(0).id,
            "title" to thisSurveyInfo.get(0).title,
            "reward" to thisSurveyInfo.get(0).reward,
            "responseDate" to thisSurveyInfo.get(0).responseDate,
            "isSent" to thisSurveyInfo.get(0).isSent

        )
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .collection("UserSurveyList").document(id)
            .set(list).addOnSuccessListener {
                Toast.makeText(this,"#####info save success", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,"#####failed", Toast.LENGTH_LONG).show()
            }

        // AndroidUser-reward_current/reward_total 업데이트
        var reward_current = 0
        var reward_total = 0
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { snapShot ->
                reward_current = Integer.parseInt(snapShot["reward_current"].toString())
                reward_total = Integer.parseInt(snapShot["reward_total"].toString())
                Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@ reward_current fetch: $reward_current")
            }
        reward_current += thisSurveyInfo.get(0).reward!!
        reward_total += thisSurveyInfo.get(0).reward!!
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .update("reward_current", reward_current, "reward_total", reward_total)


        // surveyData-respondedPanel에 currentUser uid 추가
        val item = hashMapOf(
            "uid" to Firebase.auth.currentUser!!.uid
        )
        db.collection("AndroidSurvey").document(id)
            .collection("respondedPanel").document(Firebase.auth.currentUser!!.uid)
            .set(item).addOnSuccessListener { result ->
                Log.d(TAG, "##### surveyData - respondedPanel 성공")
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
        val id: String = intent.getStringExtra("id")!!
        val imgName = id+"__"+timestamp
        val storageRef = storage.reference.child(id).child(imgName)
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





