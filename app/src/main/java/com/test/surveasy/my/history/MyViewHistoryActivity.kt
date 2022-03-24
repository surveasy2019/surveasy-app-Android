package com.test.surveasy.my.history

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.test.surveasy.databinding.ActivityMyviewhistoryBinding
import com.test.surveasy.list.FinUserSurveyListViewModel
import com.test.surveasy.list.UserSurveyItem
import com.test.surveasy.list.WaitUserSurveyListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewHistoryActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val waitModel by viewModels<WaitUserSurveyListViewModel>()
    val waitList = arrayListOf<UserSurveyItem>()
    val finModel by viewModels<FinUserSurveyListViewModel>()
    val finList = arrayListOf<UserSurveyItem>()

    private lateinit var binding : ActivityMyviewhistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewhistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.ToolbarMyViewHistory)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.ToolbarMyViewHistory.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.MyViewHistoryWait.setOnClickListener{
            binding.MyViewHistoryWaitText.setTextColor(Color.parseColor("#0aab00"))
            binding.MyViewHistoryWaitLine.visibility = View.VISIBLE
            binding.MyViewHistoryFinText.setTextColor(Color.parseColor("#707070"))
            binding.MyViewHistoryFinLine.visibility = View.INVISIBLE

            binding.fragmentContainerView1.visibility = View.VISIBLE
            binding.fragmentContainerView2.visibility = View.INVISIBLE
        }

        binding.MyViewHistoryFin.setOnClickListener{
            binding.MyViewHistoryWaitText.setTextColor(Color.parseColor("#707070"))
            binding.MyViewHistoryWaitLine.visibility = View.INVISIBLE
            binding.MyViewHistoryFinText.setTextColor(Color.parseColor("#0aab00"))
            binding.MyViewHistoryFinLine.visibility = View.VISIBLE

            binding.fragmentContainerView1.visibility = View.INVISIBLE
            binding.fragmentContainerView2.visibility = View.VISIBLE
        }

        //viewModel 에 정산 여부에 따라 나눠서 저장
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val item: UserSurveyItem = UserSurveyItem(
                        Integer.parseInt(document["lastIDChecked"].toString()),
                        document["title"] as String?,
                        Integer.parseInt(document["panelReward"].toString()),
                        document["responseDate"] as String?,
                        document["isSent"] as Boolean
                    )

                    if(document["isSent"] as Boolean){
                        finList.add(item)
                    }else{
                        waitList.add(item)
                    }
                }
                waitModel.waitSurvey.addAll(waitList)
                finModel.finSurvey.addAll(finList)


            }



    }
}