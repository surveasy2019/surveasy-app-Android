package com.example.surveasy.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

import com.example.surveasy.R
import com.example.surveasy.list.*
import com.example.surveasy.list.firstsurvey.SurveyListFirstSurveyActivity
import com.example.surveasy.login.*
import com.example.surveasy.register.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.w3c.dom.Text


class HomeFragment : Fragment() {

    val db = Firebase.firestore
    val userList = arrayListOf<UserSurveyItem>()
    private lateinit var bannerPager : ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val container : RecyclerView? = view.findViewById(R.id.homeList_recyclerView)
        val userModel by activityViewModels<CurrentUserViewModel>()
        val model by activityViewModels<SurveyInfoViewModel>()
        val current_banner: TextView = view.findViewById(R.id.textView_current_banner)
        val total_banner: TextView = view.findViewById(R.id.textView_total_banner)
        val register: Button = view.findViewById(R.id.HomeToRegister)
        val login: Button = view.findViewById(R.id.HomeToLogin)
        val greetingText: TextView = view.findViewById(R.id.Home_GreetingText)
        val totalReward: TextView = view.findViewById(R.id.Home_RewardAmount)
        val moreBtn : Button = view.findViewById(R.id.homeList_Btn)
        val noneText : TextView = view.findViewById(R.id.homeList_text)
        val img : ImageView = view.findViewById(R.id.img)



        // Banner
        bannerPager = view.findViewById(R.id.Home_BannerViewPager)
        val storage : FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef : StorageReference = storage.reference.child("banner")
        var bannerList : ArrayList<String> = ArrayList()
        val listAllTask: Task<ListResult> = storageRef.listAll()

        listAllTask.addOnSuccessListener { result ->
            val items : List<StorageReference> = result.items
            val itemNum : Int = result.items.size
            var count = 1
            total_banner.text = itemNum.toString()
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    bannerList.add(it.toString())
                     //Log.d(TAG, "UUUUUUUU${index}---$itemNum---- ${count}-${bannerList}")
                    count ++

                    if(count == itemNum) {
                        bannerPager.adapter = BannerViewPagerAdapter(context!!, bannerList)
                        bannerPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        //Log.d(TAG, "$$$$$$$%UUUUUUUU ${bannerList}")
                    }
                }

            }
        }

        bannerPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    current_banner.text = "${position+1}"
                }
            })
        }




        login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        }
        moreBtn.setOnClickListener {
            //go survey list

        }

        //user name, reward 불러오기
        if (userModel.currentUser.uid != null) {
            greetingText.text = "안녕하세요, ${userModel.currentUser.name}님!"
            totalReward.text = "$ ${userModel.currentUser.rewardTotal}"
        }
        else {
            if (Firebase.auth.currentUser?.uid != null) {
                db.collection("AndroidUser")
                    .document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener { document ->
                        greetingText.text = "안녕하세요, ${document["name"].toString()}님"
                        totalReward.text =
                            "$ ${Integer.parseInt(document["reward_total"].toString())}"
                    }
            }
            else {
                greetingText.text = "아직"
                totalReward.text = "$-----"
            }
        }

        //list 불러오기
        CoroutineScope(Dispatchers.Main).launch {
            val list = CoroutineScope(Dispatchers.IO).async {
                val model by activityViewModels<SurveyInfoViewModel>()
                while(model.surveyInfo.size==0){
                    //Log.d(TAG,"########loading")
                }
                model.surveyInfo.get(0).id
            }.await()
            val adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
            container?.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            container?.adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
            if(setHomeList(chooseHomeList()).size == 0){
                noneText.text = "현재 참여가능한 설문이 없습니다"
            }
        }

        return view
    }



    //설문 참여, 마감 유무 boolean list
    private fun chooseHomeList() : ArrayList<Boolean>{
        val userModel by activityViewModels<CurrentUserViewModel>()
        val model by activityViewModels<SurveyInfoViewModel>()
        val doneSurvey = userModel.currentUser.UserSurveyList
        var boolList = ArrayList<Boolean>(model.sortSurvey().size)
        var num: Int = 0

        //survey list item 크기와 같은 boolean type list 만들기. 모두 false 로
        while (num < model.surveyInfo.size) {
            boolList.add(false)
            num++
        }

        var index: Int = -1

        // userSurveyList 와 겹치는 요소가 있으면 boolean 배열의 해당 인덱스 값을 true로 바꿈
        if (doneSurvey?.size != 0) {
            if (doneSurvey != null) {
                for (done in doneSurvey) {
                    index = -1
                    for (survey in model.surveyInfo) {
                        index++
                        if (survey.id == done.id) {
                            boolList[index] = true
                        }else if(survey.progress >=3){
                            boolList[index] = true
                        }
                    }
                }
            }
        }else{
            index = -1
            for(survey in model.surveyInfo){
                index++
                boolList[index] = survey.progress>=3
            }
        }
        return boolList
    }

    //home list에 들어갈 list return 하기
    private fun setHomeList(boolList : ArrayList<Boolean>) : ArrayList<SurveyItems>{
        val finList = arrayListOf<SurveyItems>()
        val model by activityViewModels<SurveyInfoViewModel>()
        var index = 0
        while(index < model.surveyInfo.size){
            if(!boolList[index]){
                finList.add(model.sortSurvey().get(index))
                index+=1
            }else{
                index+=1
            }

        }
        return finList
    }




}