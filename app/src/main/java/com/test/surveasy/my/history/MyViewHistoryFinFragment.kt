package com.test.surveasy.my.history

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
import com.test.surveasy.R
import com.test.surveasy.list.FinUserSurveyListViewModel
import com.test.surveasy.list.UserSurveyItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyViewHistoryFinFragment : Fragment() {


    val db = Firebase.firestore
    //activity 내에서만 쓰이는 임시 list
    //val finTempList = arrayListOf<UserSurveyItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val finModel by activityViewModels<FinUserSurveyListViewModel>()
        val view = inflater.inflate(R.layout.fragment_myviewhistoryfin, container, false)
        val container : RecyclerView? = view.findViewById(R.id.historyFinRecyclerContainer)
        val text : TextView = view.findViewById(R.id.historyFin_noneText)
        val finReward : TextView = view.findViewById(R.id.MyViewHistory_FinAmount)
        val finMore : Button = view.findViewById(R.id.historyFin_moreBtn)
        var finTotalReward : Int = 0
        var cnt : Int = 5

        CoroutineScope(Dispatchers.Main).launch {
            val list = CoroutineScope(Dispatchers.IO).async {
                while(finModel.finSurvey.size==0){
                    Log.d(ContentValues.TAG,"########loading")
                }
                finModel.finSurvey.get(0).id
            }.await()

            for(i in finModel.finSurvey){
                finTotalReward+=i.reward!!
            }
            finReward.text = finTotalReward.toString() + "원"

            if(finModel.finSurvey.size==0){
                text.visibility = View.VISIBLE
                text.text = "해당 설문이 없습니다."
            }
            else{
                val adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
                container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                container?.adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
            }

        }

        finMore.setOnClickListener {
            if(cnt>=finModel.finSurvey.size-1){
                Toast.makeText(context,"불러올 수 있는 내역이 없습니다",Toast.LENGTH_SHORT).show()
            }else{
                cnt+=5
                val adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
                container?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                container?.adapter = FinSurveyItemsAdapter(changeHistoryList(finModel.finSurvey,cnt))
            }

        }



        return view
    }
    private fun changeHistoryList(finSurvey : ArrayList<UserSurveyItem>, cnt : Int) : ArrayList<UserSurveyItem>{
        finSurvey.sortWith(compareByDescending<UserSurveyItem> { it.responseDate })
        val defaultList = arrayListOf<UserSurveyItem>()
        var i : Int = 0
        if(finSurvey.size < cnt){
            while(i <=finSurvey.size-1){
                defaultList.add(finSurvey.get(i))
                i++
            }
        }else{
            while(i <=cnt){
                defaultList.add(finSurvey.get(i))
                i++
            }
        }
        return defaultList
    }
}