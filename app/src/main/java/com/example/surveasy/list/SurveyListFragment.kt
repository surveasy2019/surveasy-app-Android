package com.example.surveasy.list


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.login.CurrentUser
import com.example.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*


class SurveyListFragment() : Fragment() {

    val db = Firebase.firestore
    val surveyList = arrayListOf<SurveyItems>()
    val model by activityViewModels<SurveyInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylist,container,false)

        val container : RecyclerView? = view.findViewById(R.id.recyclerContainer)
        val refreshBtn : ImageButton = view.findViewById(R.id.Surveylist_refresh)



        CoroutineScope(Dispatchers.Main).launch {
            val list = CoroutineScope(Dispatchers.IO).async {
                val model by activityViewModels<SurveyInfoViewModel>()
                while(model.surveyInfo.size==0){
                    Log.d(TAG,"########loading")
                }
                model.surveyInfo.get(0).id
            }.await()
            val adapter = SurveyItemsAdapter(model.surveyInfo, changeDoneSurvey())
            container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            container?.adapter = SurveyItemsAdapter(model.surveyInfo,changeDoneSurvey())
        }
        Toast.makeText(context,"Loading",Toast.LENGTH_LONG).show()



        refreshBtn.setOnClickListener{
            (activity as MainActivity).clickList()
            Log.d(TAG,"%%%%%refresh")
        }



        return view
    }


    //참여 완료한 survey 를 list 안에서 색 변경
    private fun changeDoneSurvey() : ArrayList<Boolean> {

        val userModel by activityViewModels<CurrentUserViewModel>()
        val doneSurvey = userModel.currentUser.UserSurveyList
        var boolList = ArrayList<Boolean>(model.surveyInfo.size)
        var num: Int = 0

        //survey list item 크기와 같은 boolean type list 만들기. 모두 false 로
        while (num < model.surveyInfo.size) {
            boolList.add(false)
            num++
        }

        var index: Int = -1

        // userSurveyList 와 겹치는 요소가 있으면 boolean 배열의 해당 인덱스 값을 true로 바꿈
        if (doneSurvey != null) {
            for (done in doneSurvey) {
                index = -1
                for (survey in model.surveyInfo) {
                    index++
                    if (survey.id == done.id) {
                        boolList[index] = true
                    }
                }
            }
        }
        return boolList


    }





// firebase에서 가져와서 비교 (속도 문제)
//    private fun changeFbDone(surveyList : ArrayList<SurveyItems>) : ArrayList<Boolean>{
//        val userModel by activityViewModels<CurrentUserViewModel>()
//
//        val doneSurvey = userModel.currentUser.UserSurveyList
//        var boolList = ArrayList<Boolean>(surveyList.size)
//        var num: Int = 0
//
//        //survey list item 크기와 같은 boolean type list 만들기
//        while (num < surveyList.size) {
//            boolList.add(false)
//            num++
//        }
//
//        var index: Int = -1
//        // usersurveylist 와 겹치는 요소가 있으면 boolean 배열의 해당 인덱스 값을 true로 바꿈
//        if (doneSurvey != null) {
//            for (done in doneSurvey) {
//                index = -1
//                for (survey in surveyList) {
//                    index++
//                    if (survey.id == done.id) {
//                        boolList[index] = true
//                    }
//                }
//            }
//        }
//
//        return boolList
//    }


}