package com.app.surveasy.list

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.surveasy.MainActivity
import com.app.surveasy.databinding.ActivitySurveyprooflastdialogBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyProofLastDialogActivity : AppCompatActivity() {
    val db = Firebase.firestore


    private lateinit var binding: ActivitySurveyprooflastdialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveyprooflastdialogBinding.inflate(layoutInflater)

        setContentView(binding.root)




        binding.SurveyListDetailResponseProofBtn.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("defaultFragment_list",true)
            startActivity(intent)
            finishAffinity()

        }


        val reward = intent.getIntExtra("reward",0)

        // AndroidUser-reward_current/reward_total 업데이트
        var reward_current = 0
        var reward_total = 0
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { snapShot ->
                reward_current = Integer.parseInt(snapShot["reward_current"].toString())
                reward_total = Integer.parseInt(snapShot["reward_total"].toString())
                Log.d(ContentValues.TAG, "@@@@@@@@@@@@@@@@@@@@ reward_current fetch: $reward_current")
                reward_current += reward
                reward_total += reward

                db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                    .update("reward_current", reward_current, "reward_total", reward_total)


            }



        // surveyData-respondedPanel에 currentUser uid 추가
        val id = intent.getStringExtra("id")!!
        val dbRef = db.collection("AndroidSurvey").document(id)
        dbRef.get().addOnSuccessListener { document ->
            val respondList = document["respondedPanel"] as ArrayList<*>
            val text = document["requiredHeadCount"] as String
            val headCount = Integer.parseInt(text.substring(0,text.length-1))

            //마지막 headcount 면 progress 3으로 업데이트
            if(respondList.size==headCount) {
                dbRef.update(
                    "respondedPanel",
                    FieldValue.arrayUnion(Firebase.auth.currentUser!!.uid)
                )
                    .addOnSuccessListener { result ->
                        Log.d(TAG, "##### surveyData - respondedPanel 성공")
                    }
                dbRef.update("progress", 3)
                    .addOnSuccessListener { Log.d(TAG, "$$$ progress update 성공") }
            }else{
                dbRef.update(
                    "respondedPanel",
                    FieldValue.arrayUnion(Firebase.auth.currentUser!!.uid)
                )
                    .addOnSuccessListener { result ->
                        Log.d(TAG, "##### surveyData - respondedPanel 성공, progress 그대로")
                    }
            }
        }




    }


}




