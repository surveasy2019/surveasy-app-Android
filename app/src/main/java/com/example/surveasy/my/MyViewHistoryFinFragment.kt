package com.example.surveasy.my

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.example.surveasy.list.FinUserSurveyListViewModel
import com.example.surveasy.list.SurveyItems
import com.example.surveasy.list.SurveyItemsAdapter
import com.example.surveasy.list.UserSurveyItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewHistoryFinFragment : Fragment() {

    val finModel by activityViewModels<FinUserSurveyListViewModel>()
    val db = Firebase.firestore
    //activity 내에서만 쓰이는 임시 list
    val finTempList = arrayListOf<UserSurveyItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myviewhistoryfin, container, false)
        val container : RecyclerView? = view.findViewById(R.id.historyFinRecyclerContainer)
        val text : TextView = view.findViewById(R.id.historyFin_noneText)
        val finReward : TextView = view.findViewById(R.id.MyViewHistory_FinAmount)
        var finTotalReward : Int = 0

        //viewModel 로드 되면 viewModel 로, 아니면 firebase 로 가져오기
        if(finModel.finSurvey.size==0){
            db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                .collection("UserSurveyList").get()
                .addOnSuccessListener { documents->
                    for (document in documents) {
                        val item: UserSurveyItem = UserSurveyItem(
                            document["id"] as String,
                            document["title"] as String?,
                            Integer.parseInt(document["reward"]?.toString()),
                            document["responseDate"] as String?,
                            document["isSent"] as Boolean
                        )

                        if(document["isSent"] as Boolean){
                            finTempList.add(item)
                            finTotalReward+=Integer.parseInt(document["reward"]?.toString())
                        }
                        Toast.makeText(context,"##firestore}", Toast.LENGTH_LONG).show()

                    }
                    if(finTempList.size==0){
                        text.text = "해당 설문이 없습니다."

                    }else{
                        val adapter = FinSurveyItemsAdapter(finTempList)
                        container?.layoutManager = LinearLayoutManager(context,
                            LinearLayoutManager.VERTICAL,false)
                        container?.adapter = FinSurveyItemsAdapter(finTempList)

                    }
                    finReward.text = "$finTotalReward 원"


                }
                .addOnFailureListener{exception->
                    Log.d(ContentValues.TAG,"fail $exception")
                }
        }else{
            val adapter = FinSurveyItemsAdapter(finModel.finSurvey)
            container?.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            container?.adapter = FinSurveyItemsAdapter(finModel.finSurvey)
        }

        return view
    }
}