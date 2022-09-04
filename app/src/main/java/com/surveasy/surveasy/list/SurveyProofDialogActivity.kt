package com.surveasy.surveasy.list

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplitude.api.Amplitude
import com.surveasy.surveasy.databinding.ActivitySurveyproofdialogBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.json.JSONException
import org.json.JSONObject
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
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmm").format(Date())
    var backBtnBool :Boolean = false

    private lateinit var binding: ActivitySurveyproofdialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveyproofdialogBinding.inflate(layoutInflater)
        val id :Int = intent.getIntExtra("id",0)!!
        val idChecked :Int = intent.getIntExtra("idChecked",0)!!

        setContentView(binding.root)

        //binding.surveyProofImgScroll.smoothScrollTo(0,0)

        //설문 정보 가져와서 저장해두기
        db.collection("surveyData").document(id.toString())
            .get().addOnSuccessListener { document ->

                val item: UserSurveyItem = UserSurveyItem(
                    Integer.parseInt(document["id"].toString()) as Int,
                    Integer.parseInt(document["lastIDChecked"].toString()) as Int,
                    document["title"] as String?,
                    Integer.parseInt(document["panelReward"].toString()),
                    document["uploadDate"] as String?,
                    null,
                    false,
                    null
                )
                thisSurveyInfo.add(item)

            }.addOnFailureListener {
                //Toast.makeText(this,"*******저장 실패 ${thisSurveyInfo.toString()}",Toast.LENGTH_LONG).show()
            }

        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick, pickImageFromAlbum)

        binding.dialogSendBtn.setOnClickListener {
            binding.dialogSendBtn.visibility = View.INVISIBLE
            backBtnBool = true

            uploadStorage(binding.dialogImageview)
            updateFBInfo()
            updateReward()
            Toast.makeText(this@SurveyProofDialogActivity,"응답 제출중",Toast.LENGTH_LONG).show()

        }


        binding.dialogEditBtn.setOnClickListener {
            editPhoto()
        }

        //Toast.makeText(this,"###${id}",Toast.LENGTH_LONG).show()

        setSupportActionBar(binding.ToolbarSurveyProof)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarSurveyProof.setNavigationOnClickListener {
            onBackPressed()
        }




    }

    // 제출중 누른 후에는 뒤로가기 방지
   override fun onBackPressed() {
        if(!backBtnBool){
            super.onBackPressed()
        }

    }

    //참여한 설문 리스트 firestore에 업데이트
    private fun updateList(){

        // panelData-UserSurveyList에 참여 설문 추가
        val id: Int = intent.getIntExtra("id",0)!!
        val idChecked: Int = intent.getIntExtra("idChecked",0)!!
        // for 재첨부
        val imgName = Firebase.auth.currentUser!!.uid+"__"+timestamp
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR).toString()
        var month = (c.get(Calendar.MONTH) + 1).toString()
        var day = c.get(Calendar.DAY_OF_MONTH).toString()
        if(month.toInt() < 10) month = "0$month"
        if(day.toInt() < 10) day = "0$day"

        val date = year + "-" + month + "-" + day

        val list = hashMapOf(
            "id" to id,
            "lastIDChecked" to idChecked,
            "title" to thisSurveyInfo.get(0).title,
            "panelReward" to thisSurveyInfo.get(0).reward,
            "responseDate" to date,
            "isSent" to false,
            //filePath 추가
            "filePath" to imgName.toString()

        )
        db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
            .collection("UserSurveyList").document(idChecked.toString())
            .set(list).addOnSuccessListener {
                //Toast.makeText(this,"#####info save success", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                //Toast.makeText(this,"#####failed", Toast.LENGTH_LONG).show()
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



    //firebase storage upload
    private fun uploadStorage(view: View){

        val id: Int = intent.getIntExtra("id",0)!!
        val idChecked: Int = intent.getIntExtra("idChecked",0)!!
        val imgName = Firebase.auth.currentUser!!.uid+"__"+timestamp
//        val storageRef = storage.reference.child(idChecked.toString()).child(imgName)
        val storageRef = storage.reference.child(id.toString()).child(imgName)
        val uploadTask = storageRef.putFile(uriPhoto!!)

        uploadTask.addOnSuccessListener {
            //Toast.makeText(this,"업로드 하는 중",Toast.LENGTH_SHORT).show()
            updateList()
            val intent = Intent(this,SurveyProofLastDialogActivity::class.java)
            intent.putExtra("reward",thisSurveyInfo.get(0).reward)
            intent.putExtra("title", thisSurveyInfo.get(0).title)
            intent.putExtra("idChecked",idChecked)
            intent.putExtra("id",id)
            startActivity(intent)
        }

    }

    //사진 다시 선택
    private fun editPhoto(){
        var photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startActivityForResult(photoPick,pickImageFromAlbum)
    }

    // panelData-reward_current/reward_total 업데이트
    private fun updateReward(){
        val reward : Int = thisSurveyInfo.get(0).reward!!
        var reward_current = 0
        var reward_total = 0
        db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { snapShot ->
                reward_current = Integer.parseInt(snapShot["reward_current"].toString())
                reward_total = Integer.parseInt(snapShot["reward_total"].toString())
                Log.d(ContentValues.TAG, "@@@@@@@@@@@@@@@@@@@@ reward_current fetch: $reward_current")
                reward_current += reward
                reward_total += reward

                db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
                    .update("reward_current", reward_current, "reward_total", reward_total)


            }
    }

    // surveyData-respondedPanel에 currentUser uid 추가
    private fun updateFBInfo(){
        val id: Int = intent.getIntExtra("id",0)!!

        val dbRef = db.collection("surveyData").document(id.toString())
        dbRef.get().addOnSuccessListener { document ->
            val respondList = document["respondedPanel"] as ArrayList<*>
            val text = document["requiredHeadCount"] as String
            val headCount = Integer.parseInt(text.substring(0, text.length - 1))

            //마지막 headcount 면 progress 3으로 업데이트
            if (respondList.size >= headCount + 1) {
                dbRef.update(
                    "respondedPanel",
                    FieldValue.arrayUnion(Firebase.auth.currentUser!!.uid)
                )
                    .addOnSuccessListener { result ->
                        Log.d(ContentValues.TAG, "##### surveyData - respondedPanel 성공")
                    }
                dbRef.update("progress", 3)
                    .addOnSuccessListener { Log.d(ContentValues.TAG, "$$$ progress update 성공") }
            } else {
                dbRef.update(
                    "respondedPanel",
                    FieldValue.arrayUnion(Firebase.auth.currentUser!!.uid)
                )
                    .addOnSuccessListener { result ->
                        Log.d(
                            ContentValues.TAG,
                            "##### surveyData - respondedPanel 성공, progress 그대로"
                        )
                    }
            }
        }

    }

}





