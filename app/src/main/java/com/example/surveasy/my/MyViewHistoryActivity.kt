package com.example.surveasy.my

import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import com.example.surveasy.databinding.ActivityMyviewhistoryBinding
import com.example.surveasy.list.FinUserSurveyListViewModel
import com.example.surveasy.list.UserSurveyItem
import com.example.surveasy.list.WaitUserSurveyListViewModel
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

        binding.MyViewHistoryWaitBtn.setOnClickListener{
            binding.fragmentContainerView1.visibility = View.VISIBLE
            binding.fragmentContainerView2.visibility = View.INVISIBLE
        }

        binding.MyViewHistoryFinBtn.setOnClickListener{
            binding.fragmentContainerView1.visibility = View.INVISIBLE
            binding.fragmentContainerView2.visibility = View.VISIBLE
        }

        //viewModel 에 정산 여부에 따라 나눠서 저장
        db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
            .collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val item: UserSurveyItem = UserSurveyItem(
                        document["id"] as String,
                        document["title"] as String?,
                        Integer.parseInt(document["reward"]?.toString()),
                        document["uploadDate"] as String?,
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