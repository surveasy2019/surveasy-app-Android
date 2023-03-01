package com.surveasy.surveasy.home

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.amplitude.api.Amplitude

import com.surveasy.surveasy.list.*
import com.surveasy.surveasy.login.*
import com.surveasy.surveasy.home.Opinion.HomeOpinionDetailActivity
import com.surveasy.surveasy.home.Opinion.HomeOpinionViewModel
import com.surveasy.surveasy.home.banner.BannerViewModel
import com.surveasy.surveasy.home.banner.BannerViewPagerAdapter
import com.surveasy.surveasy.home.contribution.ContributionItemsAdapter
import com.surveasy.surveasy.home.contribution.HomeContributionViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.surveasy.surveasy.*
import com.surveasy.surveasy.auth.AuthDialogActivity
import com.surveasy.surveasy.auth.AuthProcessActivity
import com.surveasy.surveasy.auth.loginWithKakao
import com.surveasy.surveasy.home.Opinion.HomeOpinionAnswerActivity
import com.surveasy.surveasy.home.Opinion.HomeOpinionAnswerViewModel
import com.surveasy.surveasy.my.history.MyViewHistoryActivity
import kotlinx.coroutines.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Runnable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate


class HomeFragment : Fragment() {

    val db = Firebase.firestore
    val storage = Firebase.storage
    val userList = arrayListOf<UserSurveyItem>()
    private lateinit var bannerPager : ViewPager2
    private lateinit var mContext: Context
    private val userModel by activityViewModels<CurrentUserViewModel>()
    private val bannerModel by activityViewModels<BannerViewModel>()
    private val contributionModel by activityViewModels<HomeContributionViewModel>()
    private val opinionModel by activityViewModels<HomeOpinionViewModel>()
    private val answerModel by activityViewModels<HomeOpinionAnswerViewModel>()
    private val model by activityViewModels<SurveyInfoViewModel>()

    //auth check
    private lateinit var mainViewModel : MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var left = 0
        var right = 1
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val container : RecyclerView? = view.findViewById(R.id.homeList_recyclerView)
        val contributionContainer : RecyclerView = view.findViewById(R.id.HomeContribution_recyclerView)
        val current_banner: TextView = view.findViewById(R.id.textView_current_banner)
        val total_banner: TextView = view.findViewById(R.id.textView_total_banner)
//        val springDotsIndicator: SpringDotsIndicator = view.findViewById(R.id.Home_spring_dots_indicator)
        val firstSurveyContainer : LinearLayout = view.findViewById(R.id.HomeList_ItemContainer_first)
        val firstSurveyTitle : TextView = view.findViewById(R.id.HomeListItem_Title_first)
        val homeListContainer: RecyclerView = view.findViewById(R.id.homeList_recyclerView)
        val noneText : TextView = view.findViewById(R.id.homeList_text)
        val greetingText: TextView = view.findViewById(R.id.Home_GreetingText)
        val surveyNum: TextView = view.findViewById(R.id.Home_SurveyNum)
        val totalReward: TextView = view.findViewById(R.id.Home_RewardAmount)
        val moreBtn : TextView = view.findViewById(R.id.homeList_Btn)
        val homeTopBox : LinearLayout = view.findViewById(R.id.Home_parSurvey_box)
        val opinionContainer: LinearLayout = view.findViewById(R.id.Home_Opinion_Q_Container)
        val opinionTextView : TextView = view.findViewById(R.id.Home_Opinion_TextView)
        val opinionAnswerL : LinearLayout = view.findViewById(R.id.Home_Poll_answer_containerL)
        val opinionAnswerR : LinearLayout = view.findViewById(R.id.Home_Poll_answer_containerR)
        var answerTitleL : TextView = view.findViewById(R.id.Home_Opinion_Answer_Title_L)
        var answerTitleR : TextView = view.findViewById(R.id.Home_Opinion_Answer_Title_R)
        val answerLBtn : LinearLayout = view.findViewById(R.id.Home_Opinion_L)
        val answerRBtn : LinearLayout = view.findViewById(R.id.Home_Opinion_R)

        // fetch auth info
        mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        
        CoroutineScope(Dispatchers.Main).launch { 
            mainViewModel.fetchDidAuth(Firebase.auth.uid.toString())
            mainViewModel.repositories1.observe(viewLifecycleOwner){
                if(!it.didAuth){
                    val intent = Intent(context, AuthDialogActivity::class.java)
                    startActivity(intent)
                }
            }
        }


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

            bannerDefault.visibility = View.INVISIBLE
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

        homeTopBox.setOnClickListener {
            val intent = Intent(context, AuthDialogActivity::class.java)
            startActivity(intent)

//            val intent = Intent(context, MyViewHistoryActivity::class.java)
//            startActivity(intent)
            
        }



        moreBtn.setOnClickListener {
            if (userModel.currentUser.didFirstSurvey == false) {
                (activity as MainActivity).navColor_in_Home()
                (activity as MainActivity).moreBtn()

            } else {
                (activity as MainActivity).clickList()
                (activity as MainActivity).navColor_in_Home()
            }
        }




        //user name, reward 불러오기
        if (userModel.currentUser.uid != null) {
            greetingText.text = "안녕하세요, ${userModel.currentUser.name}님!"
            if(userModel.currentUser.UserSurveyList == null){
                surveyNum.text = "0개"
            }else{
                surveyNum.text = "${userModel.currentUser.UserSurveyList!!.size}개"
            }

            totalReward.text = "${userModel.currentUser.rewardTotal}원"
        } else {
            if (Firebase.auth.currentUser?.uid != null) {
                //query 보다 원래 방법이 더 빠름
                db.collection("panelData")
                    .document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener { document ->
                        greetingText.text = "안녕하세요, ${document["name"].toString()}님"
                        totalReward.text =
                            "${(Integer.parseInt(document["reward_total"].toString()))}원"
                    }

                db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
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
            //Log.d(TAG, "onCreateView: 시작 전")
            //val model by activityViewModels<SurveyInfoViewModel>()
            getHomeList(model)
            //Log.d(TAG, "onCreateView: 끝")


            if (userModel.currentUser.didFirstSurvey == false) {
                firstSurveyContainer.visibility= View.VISIBLE
                noneText.visibility = View.GONE
                homeListContainer.visibility = View.GONE
                if (userModel.currentUser.uid != null) {
                    firstSurveyTitle.text = "${userModel.currentUser.name}님에 대해 알려주세요!"
                }
                else {
                    if (Firebase.auth.currentUser?.uid != null) {
                        db.collection("panelData")
                            .document(Firebase.auth.currentUser!!.uid)
                            .get().addOnSuccessListener { document ->
                                firstSurveyTitle.text = "${document["name"].toString()}님에 대해 알려주세요!"
                            }
                    }
                }

                firstSurveyContainer.setOnClickListener{
                    (activity as MainActivity).navColor_in_Home()
                    (activity as MainActivity).moreBtn()
                }

            }

            else if(userModel.currentUser.didFirstSurvey == true) {
                if (setHomeList(chooseHomeList()).size == 0) {
                    firstSurveyContainer.visibility= View.GONE
                    homeListContainer.visibility = View.GONE

                    noneText.text = "현재 참여가능한 설문이 없습니다"
                    noneText.visibility = View.VISIBLE
                }

                else {
                    firstSurveyContainer.visibility= View.GONE
                    noneText.visibility = View.GONE
                    homeListContainer.visibility = View.VISIBLE
                    val adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
                    container?.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL, false
                    )
                    container?.adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
                }

            }

        }


        // Contribution
        CoroutineScope(Dispatchers.Main).launch {
            val contributionList = CoroutineScope(Dispatchers.IO).async {
                while(contributionModel.contributionList.size == 0) { }
                contributionModel.contributionList
            }.await()

            contributionContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            contributionContainer.adapter = ContributionItemsAdapter(contributionList)

        }


        // Opinion
        CoroutineScope(Dispatchers.Main).launch {
            val opinionItem = CoroutineScope(Dispatchers.IO).async {
                while(opinionModel.opinionItem.question == null) { }
                opinionModel.opinionItem
            }.await()

            opinionTextView.text = opinionModel.opinionItem.question
        }

        opinionContainer.setOnClickListener {
            val intent = Intent(context, HomeOpinionDetailActivity::class.java)
            intent.putExtra("id", opinionModel.opinionItem.id)
            intent.putExtra("question", opinionModel.opinionItem.question)
            intent.putExtra("content1", opinionModel.opinionItem.content1)
            intent.putExtra("content2", opinionModel.opinionItem.content2)
            startActivity(intent)

            // [Amplitude] Poll View Showed
            val client = Amplitude.getInstance()
            client.logEvent("Poll View Showed")
        }



        //null 오류 다시 체크
        CoroutineScope(Dispatchers.Main).launch {
            val list : Int? = CoroutineScope(Dispatchers.IO).async {
                val model by activityViewModels<HomeOpinionAnswerViewModel>()
                while (model.homeAnswerList.size == 0) {
                    //Log.d(TAG, "########loading")
                }
//                model.homeAnswerList.get(0).id
                1

            }.await()

            activity?.runOnUiThread(Runnable {
                if(answerModel.homeAnswerList.get(left).id==2){

                }
                answerTitleL.text = answerModel.homeAnswerList.get(left).question.toString()
                answerTitleR.text = answerModel.homeAnswerList.get(right).question.toString()

                answerRBtn.setOnClickListener{
                    if(right<answerModel.homeAnswerList.size-1){
                        left++
                        right++
                        answerTitleL.text = answerModel.homeAnswerList.get(left).question.toString()
                        answerTitleR.text = answerModel.homeAnswerList.get(right).question.toString()
                    }

                }
                answerLBtn.setOnClickListener{
                    if(left>0){
                        left--
                        right--
                        answerTitleL.text = answerModel.homeAnswerList.get(left).question.toString()
                        answerTitleR.text = answerModel.homeAnswerList.get(right).question.toString()
                    }

                }
            })
        }


        opinionAnswerL.setOnClickListener {
            if(answerModel.homeAnswerList.get(left).id!=2){
                val intent = Intent(context, HomeOpinionAnswerActivity::class.java)
                intent.putExtra("id", answerModel.homeAnswerList.get(left).id)
                intent.putExtra("content1",answerModel.homeAnswerList.get(left).content1)
                intent.putExtra("content2",answerModel.homeAnswerList.get(left).content2)
                intent.putExtra("content3",answerModel.homeAnswerList.get(left).content3)

                putAnswerItemNum(intent, answerModel.homeAnswerList.get(left).id)

            }


            // [Amplitude] Poll_Answer View Showed
            val client = Amplitude.getInstance()
            client.logEvent("Poll_Answer View Showed")
        }


        opinionAnswerR.setOnClickListener {
            if(answerModel.homeAnswerList.get(right).id!=2){
                val intent = Intent(context, HomeOpinionAnswerActivity::class.java)
                intent.putExtra("id", answerModel.homeAnswerList.get(right).id)
                intent.putExtra("content1",answerModel.homeAnswerList.get(right).content1)
                intent.putExtra("content2",answerModel.homeAnswerList.get(right).content2)
                intent.putExtra("content3",answerModel.homeAnswerList.get(right).content3)

                putAnswerItemNum(intent, answerModel.homeAnswerList.get(right).id)
            }

            // [Amplitude] Poll_Answer View Showed
            val client = Amplitude.getInstance()
            client.logEvent("Poll_Answer View Showed")
        }

            return view
    }






    //설문 참여, 마감 유무 boolean list
    private fun chooseHomeList() : ArrayList<Boolean>{
//        val userModel by activityViewModels<CurrentUserViewModel>()
//        val model by activityViewModels<SurveyInfoViewModel>()
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
                        //homelist 마감 체크
                        val dueDate = survey.dueDate + " " + survey.dueTimeTime + ":00"
                        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val date = sf.parse(dueDate)
                        val now = Calendar.getInstance()
                        val calDate = (date.time - now.time.time) / (60 * 60 * 1000)

                        if(calDate<0){
                            boolList[index] = true
                        }
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
        //val model by activityViewModels<SurveyInfoViewModel>()
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
    //Coroutine test -ing
//    private suspend fun getBannerImg(bannerModel : BannerViewModel){
//        withContext(Dispatchers.IO){
//            Log.d(TAG, "########coroutine ${print("where")}")
//            while (bannerModel.uriList.size == 0) {
//                //bannerDefault.visibility = View.VISIBLE
//            }
//        }
//    }
    private suspend fun getHomeList(listModel : SurveyInfoViewModel){
        withContext(Dispatchers.IO){
            while (listModel.surveyInfo.size == 0) {
                //Log.d(TAG, "########loading")
            }
        }
    }
//
//    fun <T>print(msg : T){
//        kotlin.io.println("$msg [${Thread.currentThread().name}")
//    }


    // 해당 id의 Opinion Answer의 Storage 이미지 개수 불러와서 HomeOpinionAnswerActivity에 전달
    private fun putAnswerItemNum(intent: Intent, id : Int) {
        val urlList = ArrayList<String>()
        CoroutineScope(Dispatchers.Main).launch {
            val url = CoroutineScope(Dispatchers.IO).async {
                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                val storageRef: StorageReference = storage.reference.child("AppOpinionAnswerImage").child(id.toString())
                val listAllTask: Task<ListResult> = storageRef.listAll()

                var itemNum : Int = 0    // 해당 id의 Answer이 가진 이미지 개수
                listAllTask.addOnSuccessListener { result ->
                    result.items.forEach { it ->
                        itemNum++
                    }

                    intent.putExtra("itemNum", itemNum)
                    startActivity(intent)
                }
                itemNum
            }.await()
        }
    }




}