package com.example.surveasy.list


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SurveyListFirstSurvey2Fragment() : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylistfirstsurvey2,container,false)
        val userModel by activityViewModels<CurrentUserViewModel>()

        val surveyListFirstSurvey2Btn : Button = view.findViewById(R.id.SurveyListFirstSurvey2_Btn)

        surveyListFirstSurvey2Btn.setOnClickListener{
            //finFirstSurvey(userModel.currentUser!!.uid!!)
            // update Firestore 'firstSurvey"
            db.collection("AndroidUser").document(userModel.currentUser.uid!!)
                .update("firstSurvey", true)
                .addOnSuccessListener { Log.d(TAG, "@@@@@DocumentSnapshot successfully updated!") }
            Log.d(TAG, "@@@@@ ${userModel.currentUser.uid}")
            val intent : Intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun finFirstSurvey(uid: String) {

    }


}