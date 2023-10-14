package com.surveasy.surveasy.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
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
import com.surveasy.surveasy.*
import com.surveasy.surveasy.auth.AuthDialogActivity
import com.surveasy.surveasy.databinding.FragmentHomeBinding
import com.surveasy.surveasy.home.Opinion.HomeOpinionAnswerActivity
import com.surveasy.surveasy.home.Opinion.HomeOpinionAnswerViewModel
import com.surveasy.surveasy.home.list.HomeListItemsAdapter
import com.surveasy.surveasy.my.history.MyViewHistoryActivity
import kotlinx.coroutines.*
import java.lang.Runnable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    val db = Firebase.firestore
    val storage = Firebase.storage
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentHomeBinding.inflate(layoutInflater)

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

        Glide.with(this@HomeFragment).load(R.raw.app_loading).into(binding.HomeBannerDefault)
        CoroutineScope(Dispatchers.Main).launch {
            val banner = CoroutineScope(Dispatchers.IO).async {
                while (bannerModel.uriList.size == 0) {
                    //bannerDefault.visibility = View.VISIBLE
                }
                binding.HomeBannerDefault.visibility = View.INVISIBLE
                bannerModel.uriList

            }.await()

            binding.HomeBannerDefault.visibility = View.INVISIBLE
            binding.textViewTotalBanner.text = bannerModel.num.toString()
            binding.HomeBannerViewPager.offscreenPageLimit = bannerModel.num
            binding.HomeBannerViewPager.adapter = BannerViewPagerAdapter(mContext, bannerModel.uriList)
            binding.HomeBannerViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        }


        // Banner 넘기면 [현재 페이지/전체 페이지] 변화
        binding.HomeBannerViewPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.textViewCurrentBanner.text = "${position+1}"
                }
            })
        }

        binding.HomeParSurveyBox.setOnClickListener {

            val intent = Intent(context, MyViewHistoryActivity::class.java)
            startActivity(intent)
            
        }



        binding.homeListBtn.setOnClickListener {
            if (userModel.currentUser.didFirstSurvey == false) {
                (activity as MainActivity).navColor_In_Home()
                (activity as MainActivity).moreBtn()

            } else {
                (activity as MainActivity).clickList()
                (activity as MainActivity).navColor_In_Home()
            }
        }




        //user name, reward 불러오기
        if (userModel.currentUser.uid != null) {
            binding.HomeGreetingText.text = "안녕하세요, ${userModel.currentUser.name}님!"
            if(userModel.currentUser.UserSurveyList == null){
                binding.HomeSurveyNum.text = "0개"
            }else{
                binding.HomeSurveyNum.text = "${userModel.currentUser.UserSurveyList!!.size}개"
            }

            binding.HomeRewardAmount.text = "${userModel.currentUser.rewardTotal}원"
        } else {
            if (Firebase.auth.currentUser?.uid != null) {
                //query 보다 원래 방법이 더 빠름
                db.collection("panelData")
                    .document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener { document ->
                        binding.HomeGreetingText.text = "안녕하세요, ${document["name"].toString()}님"
                        binding.HomeRewardAmount.text =
                            "${(Integer.parseInt(document["reward_total"].toString()))}원"
                    }

                db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
                    .collection("UserSurveyList").get()
                    .addOnSuccessListener { document ->
                        var num = 0
                        for(item in document) {
                            num++
                        }
                        binding.HomeSurveyNum.text = num.toString() + "개"
                    }

            } else {
                binding.HomeGreetingText.text = "아직"
                binding.HomeRewardAmount.text = "$-----"
            }
        }

        //list 불러오기
        CoroutineScope(Dispatchers.Main).launch {
            //Log.d(TAG, "onCreateView: 시작 전")
            //val model by activityViewModels<SurveyInfoViewModel>()
            getHomeList(model)
            //Log.d(TAG, "onCreateView: 끝")


            if (userModel.currentUser.didFirstSurvey == false) {
                binding.HomeListItemContainerFirst.visibility= View.VISIBLE
                binding.homeListText.visibility = View.GONE
                binding.homeListRecyclerView.visibility = View.GONE
                if (userModel.currentUser.uid != null) {
                    binding.HomeListItemTitleFirst.text = "${userModel.currentUser.name}님에 대해 알려주세요!"
                }
                else {
                    if (Firebase.auth.currentUser?.uid != null) {
                        db.collection("panelData")
                            .document(Firebase.auth.currentUser!!.uid)
                            .get().addOnSuccessListener { document ->
                                binding.HomeListItemTitleFirst.text = "${document["name"].toString()}님에 대해 알려주세요!"
                            }
                    }
                }

                binding.HomeListItemContainerFirst.setOnClickListener{
                    (activity as MainActivity).navColor_In_Home()
                    (activity as MainActivity).moreBtn()
                }

            }

            else if(userModel.currentUser.didFirstSurvey == true) {
                if (setHomeList(chooseHomeList()).size == 0) {
                    binding.HomeListItemContainerFirst.visibility= View.GONE
                    binding.homeListRecyclerView.visibility = View.GONE

                    binding.homeListText.text = "현재 참여가능한 설문이 없습니다"
                    binding.homeListText.visibility = View.VISIBLE
                }

                else {
                    binding.HomeListItemContainerFirst.visibility= View.GONE
                    binding.homeListText.visibility = View.GONE
                    binding.homeListRecyclerView.visibility = View.VISIBLE
                    val adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
                    binding.homeListRecyclerView.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL, false
                    )
                    binding.homeListRecyclerView.adapter = HomeListItemsAdapter(setHomeList(chooseHomeList()))
                }

            }

        }


        // Contribution
        CoroutineScope(Dispatchers.Main).launch {
            val contributionList = CoroutineScope(Dispatchers.IO).async {
                while(contributionModel.contributionList.size == 0) { }
                contributionModel.contributionList
            }.await()

            binding.HomeContributionRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.HomeContributionRecyclerView.adapter = ContributionItemsAdapter(contributionList)

        }


        // Opinion
        CoroutineScope(Dispatchers.Main).launch {
            val opinionItem = CoroutineScope(Dispatchers.IO).async {
                while(opinionModel.opinionItem.question == null) { }
                opinionModel.opinionItem
            }.await()

            binding.HomeOpinionTextView.text = opinionModel.opinionItem.question
        }

        binding.HomeOpinionQContainer.setOnClickListener {
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
                binding.HomeOpinionAnswerTitleL.text = answerModel.homeAnswerList.get(left).question.toString()
                binding.HomeOpinionAnswerTitleR.text = answerModel.homeAnswerList.get(right).question.toString()

                binding.HomeOpinionR.setOnClickListener{
                    if(right<answerModel.homeAnswerList.size-1){
                        left++
                        right++
                        binding.HomeOpinionAnswerTitleL.text = answerModel.homeAnswerList.get(left).question.toString()
                        binding.HomeOpinionAnswerTitleR.text = answerModel.homeAnswerList.get(right).question.toString()
                    }

                }
                binding.HomeOpinionL.setOnClickListener{
                    if(left>0){
                        left--
                        right--
                        binding.HomeOpinionAnswerTitleL.text = answerModel.homeAnswerList.get(left).question.toString()
                        binding.HomeOpinionAnswerTitleR.text = answerModel.homeAnswerList.get(right).question.toString()
                    }

                }
            })
        }


        binding.HomePollAnswerContainerL.setOnClickListener {
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


        binding.HomePollAnswerContainerR.setOnClickListener {
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

            return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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