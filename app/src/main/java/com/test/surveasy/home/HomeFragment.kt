package com.test.surveasy.home

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.test.surveasy.MainActivity

import com.test.surveasy.R
import com.test.surveasy.list.*
import com.test.surveasy.login.*
import com.test.surveasy.home.Opinion.HomeOpinionDetailActivity
import com.test.surveasy.home.Opinion.HomeOpinionViewModel
import com.test.surveasy.home.banner.BannerViewModel
import com.test.surveasy.home.banner.BannerViewPagerAdapter
import com.test.surveasy.home.contribution.ContributionItemsAdapter
import com.test.surveasy.home.contribution.HomeContributionViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    val db = Firebase.firestore
    val userList = arrayListOf<UserSurveyItem>()
    private lateinit var bannerPager : ViewPager2
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val container : RecyclerView? = view.findViewById(R.id.homeList_recyclerView)
        val contributionContainer : RecyclerView = view.findViewById(R.id.HomeContribution_recyclerView)
        val userModel by activityViewModels<CurrentUserViewModel>()
        val bannerModel by activityViewModels<BannerViewModel>()
        val contributionModel by activityViewModels<HomeContributionViewModel>()
        val opinionModel by activityViewModels<HomeOpinionViewModel>()
        val model by activityViewModels<SurveyInfoViewModel>()
        val current_banner: TextView = view.findViewById(R.id.textView_current_banner)
        val total_banner: TextView = view.findViewById(R.id.textView_total_banner)
//        val springDotsIndicator: SpringDotsIndicator = view.findViewById(R.id.Home_spring_dots_indicator)
        //val register: Button = view.findViewById(R.id.HomeToRegister)
        //val login: Button = view.findViewById(R.id.HomeToLogin)
        val greetingText: TextView = view.findViewById(R.id.Home_GreetingText)
        val surveyNum: TextView = view.findViewById(R.id.Home_SurveyNum)
        val totalReward: TextView = view.findViewById(R.id.Home_RewardAmount)
        val moreBtn : TextView = view.findViewById(R.id.homeList_Btn)
        val noneText : TextView = view.findViewById(R.id.homeList_text)
        val opinionTextView : TextView = view.findViewById(R.id.Home_Opinion_TextView)


        // Banner init
        bannerPager = view.findViewById(R.id.Home_BannerViewPager)
        val bannerDefault : ImageView = view.findViewById(R.id.Home_BannerDefault)

        Glide.with(this@HomeFragment).load(R.raw.app_loading).into(bannerDefault)
        CoroutineScope(Dispatchers.Main).launch {
            val banner = CoroutineScope(Dispatchers.IO).async {
                while (bannerModel.uriList.size == 0) {
                    //bannerDefault.visibility = View.VISIBLE
                }
                bannerDefault.visibility = View.INVISIBLE
                bannerModel.uriList

            }.await()

            total_banner.text = bannerModel.num.toString()
            bannerPager.offscreenPageLimit = bannerModel.num
            bannerPager.adapter = BannerViewPagerAdapter(mContext, bannerModel.uriList)
            bannerPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        }


        // Banner 넘기면 [현재 페이지/전체 페이지] 변화
        bannerPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    current_banner.text = "${position+1}"
                }
            })
        }





    //    login.setOnClickListener {
    //        //val intent = Intent(context, LoginActivity::class.java)
    //        val intent = Intent(context, FirstIntroduceScreenActivity::class.java)
    //        startActivity(intent)
    //    }


    //    register.setOnClickListener {
    //        val intent = Intent(context, RegisterActivity::class.java)
    //
     //       startActivity(intent)
    //    }


        moreBtn.setOnClickListener {
            if (userModel.currentUser.didFirstSurvey == false) {
                (activity as MainActivity).navColor_in_Home()
                (activity as MainActivity).moreBtn()

//                val intent_surveylistfirstsurvey: Intent =
//                    Intent(context, FirstSurveyListActivity::class.java)
//                intent_surveylistfirstsurvey.putExtra("currentUser_main", userModel.currentUser)
//                startActivity(intent_surveylistfirstsurvey)

            } else {
                (activity as MainActivity).clickList()
                (activity as MainActivity).navColor_in_Home()
            }
        }


        //user name, reward 불러오기
        if (userModel.currentUser.uid != null) {
            greetingText.text = "안녕하세요, ${userModel.currentUser.name}님!"
            surveyNum.text = "${userModel.currentUser.UserSurveyList!!.size}개"
            totalReward.text = "${userModel.currentUser.rewardTotal}원"
        } else {
            if (Firebase.auth.currentUser?.uid != null) {
                db.collection("AndroidUser")
                    .document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener { document ->
                        greetingText.text = "안녕하세요, ${document["name"].toString()}님"
                        totalReward.text =
                            "${(Integer.parseInt(document["reward_total"].toString()))}원"
                    }

                db.collection("AndroidUser").document(Firebase.auth.currentUser!!.uid)
                    .collection("UserSurveyList").get()
                    .addOnSuccessListener { document ->
                        var num = 0
                        for(item in document) {
                            num++
                        }
                        surveyNum.text = num.toString() + "개"
                    }

            } else {
                greetingText.text = "아직"
                totalReward.text = "$-----"
            }
        }

        //list 불러오기
        CoroutineScope(Dispatchers.Main).launch {
            val list = CoroutineScope(Dispatchers.IO).async {
                val model by activityViewModels<SurveyInfoViewModel>()
                while (model.surveyInfo.size == 0) {
                    //Log.d(TAG, "########loading")
                }
                model.surveyInfo.get(0).id
            }.await()

            if (setHomeList(chooseHomeList()).size == 0 || userModel.currentUser.didFirstSurvey == false) {
                noneText.text = "현재 참여가능한 설문이 없습니다"
                noneText.visibility = View.VISIBLE
            }else{
                val adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
                container?.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
                container?.adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
            }
        }


        // Contribution
        CoroutineScope(Dispatchers.Main).launch {
            val contributionList = CoroutineScope(Dispatchers.IO).async {
                while(contributionModel.contributionList.size == 0) { }
                contributionModel.contributionList
            }.await()

            Log.d(TAG, "+++++++++++ ${contributionList}")
            contributionContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            contributionContainer.adapter = ContributionItemsAdapter(contributionList)

        }


        // Opinion
        CoroutineScope(Dispatchers.Main).launch {
            val opinionItem = CoroutineScope(Dispatchers.IO).async {
                while(opinionModel.opinionItem.question == null) { }
                opinionModel.opinionItem
            }.await()

            Log.d(TAG, "+++++++++++ ${opinionModel.opinionItem.question}")
            opinionTextView.text = opinionModel.opinionItem.question

        }

        opinionTextView.setOnClickListener {
            val intent = Intent(context, HomeOpinionDetailActivity::class.java)
            intent.putExtra("id", opinionModel.opinionItem.id)
            intent.putExtra("question", opinionModel.opinionItem.question)
            intent.putExtra("content1", opinionModel.opinionItem.content1)
            intent.putExtra("content2", opinionModel.opinionItem.content2)

            startActivity(intent)
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
                        if (survey.id.equals(done.id)) {
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