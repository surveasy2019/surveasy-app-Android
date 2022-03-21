package com.app.surveasy.my.history

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.surveasy.R
import com.app.surveasy.list.UserSurveyItem
import com.app.surveasy.list.WaitUserSurveyListViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MyViewHistoryWaitFragment : Fragment() {


    val db = Firebase.firestore
    //activity 내에서만 쓰이는 임시 list
    val waitTempList = arrayListOf<UserSurveyItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val waitModel by activityViewModels<WaitUserSurveyListViewModel>()
        val view = inflater.inflate(R.layout.fragment_myviewhistorywait, container, false)
        val container :RecyclerView? = view.findViewById(R.id.historyWaitRecyclerContainer)
        val text : TextView = view.findViewById(R.id.historyWait_noneText)
        val waitReward : TextView = view.findViewById(R.id.MyViewHistory_WaitAmount)
        val waitMore : Button = view.findViewById(R.id.historyWait_moreBtn)
        var waitTotalReward : Int = 0
        var cnt : Int = 0


        CoroutineScope(Dispatchers.Main).launch {
            val list = CoroutineScope(Dispatchers.IO).async {
                while(waitModel.waitSurvey.size==0){
                    Log.d(ContentValues.TAG,"########loading")
                }
                waitModel.waitSurvey.get(0).id
            }.await()
            for(i in waitModel.waitSurvey){
                waitTotalReward+=i.reward!!
            }
            waitReward.text = waitTotalReward.toString() + "원"

            if(waitModel.waitSurvey.size==0){
                text.visibility = View.VISIBLE
                text.text = "해당 설문이 없습니다."
            }
            else{
                val adapter = FinSurveyItemsAdapter(changeHistoryList(waitModel.waitSurvey,cnt))
                container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                container?.adapter = FinSurveyItemsAdapter(changeHistoryList(waitModel.waitSurvey,cnt))
            }

        }


        waitMore.setOnClickListener {
            if(cnt>=waitModel.waitSurvey.size-1){
                Toast.makeText(context,"불러올 수 있는 내역이 없습니다",Toast.LENGTH_LONG).show()
            }else{
                cnt+=1
                val adapter = FinSurveyItemsAdapter(changeHistoryList(waitModel.waitSurvey,cnt))
                container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                container?.adapter = FinSurveyItemsAdapter(changeHistoryList(waitModel.waitSurvey,cnt))
            }

        }
        

        return view
    }

    private fun changeHistoryList(waitSurvey : ArrayList<UserSurveyItem>, cnt : Int) : ArrayList<UserSurveyItem>{
        waitSurvey.sortWith(compareByDescending<UserSurveyItem> { it.responseDate })
        val defaultList = arrayListOf<UserSurveyItem>()
        var i : Int = 0
        while(i <=cnt){
            defaultList.add(waitSurvey.get(i))
            i++
        }
        return defaultList
    }
}