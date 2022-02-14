package com.example.surveasy.my

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surveasy.R
import com.example.surveasy.list.UserSurveyItem
import com.example.surveasy.list.WaitUserSurveyListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MyViewHistoryWaitFragment : Fragment() {

    val waitModel by activityViewModels<WaitUserSurveyListViewModel>()
    val db = Firebase.firestore
    //activity 내에서만 쓰이는 임시 list
    val waitTempList = arrayListOf<UserSurveyItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myviewhistorywait, container, false)
        val container :RecyclerView? = view.findViewById(R.id.historyWaitRecyclerContainer)
        val text : TextView = view.findViewById(R.id.historyWait_noneText)
        val waitReward : TextView = view.findViewById(R.id.MyViewHistory_WaitAmount)
        var waitTotalReward : Int = 0


        //viewModel 로드 되면 viewModel 로, 아니면 firebase 로 가져오기
        if(waitModel.waitSurvey.size==0){
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
                        if(!(document["isSent"] as Boolean)){
                            waitTempList.add(item)
                            waitTotalReward+=Integer.parseInt(document["reward"]?.toString())
                        }
                        Toast.makeText(context,"##firestore}", Toast.LENGTH_LONG).show()

                    }
                    if(waitTempList.size==0){
                        text.text = "해당 설문이 없습니다."

                    }else{
                        val adapter = FinSurveyItemsAdapter(waitTempList)
                        container?.layoutManager = LinearLayoutManager(context,
                            LinearLayoutManager.VERTICAL,false)
                        container?.adapter = FinSurveyItemsAdapter(waitTempList)
                    }
                    waitReward.text = "$waitTotalReward 원"


                }
                .addOnFailureListener{exception->
                    Log.d(ContentValues.TAG,"fail $exception")
                }
        }else{
            val adapter = FinSurveyItemsAdapter(waitModel.waitSurvey)
            container?.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            container?.adapter = FinSurveyItemsAdapter(waitModel.waitSurvey)
        }
        

        return view
    }
}