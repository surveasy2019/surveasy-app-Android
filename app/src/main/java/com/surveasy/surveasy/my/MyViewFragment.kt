package com.surveasy.surveasy.my


import android.os.Bundle
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.surveasy.surveasy.R
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.room.Room
import com.amplitude.api.Amplitude
import com.surveasy.surveasy.login.CurrentUserViewModel
import com.surveasy.surveasy.my.history.MyViewHistoryActivity
import com.surveasy.surveasy.my.info.InfoData
import com.surveasy.surveasy.my.info.InfoDataViewModel
import com.google.firebase.auth.ktx.auth
import com.surveasy.surveasy.my.info.MyViewInfoActivity
import com.surveasy.surveasy.my.setting.MyViewSettingActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.my.notice.*
import com.surveasy.surveasy.my.notice.noticeRoom.NoticeDatabase
import kotlinx.coroutines.*


class MyViewFragment : Fragment() {
    val db = Firebase.firestore
    val infoDataModel by viewModels<InfoDataViewModel>()
    var info = InfoData(null, null, null, null, null, null, null, null)
    var noticeNum_fb = 0
    var noticeNum_room = 0
    val userModel by activityViewModels<CurrentUserViewModel>()
    private lateinit var noticeDB : NoticeDatabase

    override fun onStart() {
        super.onStart()
        val infoIcon = requireView().findViewById<LinearLayout>(R.id.MyView_InfoIcon)
        val noticeDot = requireView().findViewById<ImageView>(R.id.MyView_NoticeIcon_dot)
        val noticeBtn = requireView().findViewById<ImageView>(R.id.MyView_NoticeIcon)
        val userName = requireView().findViewById<TextView>(R.id.MyView_UserName)
        val userRewardFinAmount = requireView().findViewById<TextView>(R.id.MyView_UserRewardFinAmount)
        val userRewardYetAmount = requireView().findViewById<TextView>(R.id.MyView_UserRewardYetAmount)
        val userSurveyCountAmount = requireView().findViewById<TextView>(R.id.MyView_UserSurveyCountAmount)

        CoroutineScope(Dispatchers.Main).launch {
            val myInfo = CoroutineScope(Dispatchers.IO).async {
                fetchInfoData()
            }.await()

            infoIcon.setOnClickListener {
                val intent = Intent(context, MyViewInfoActivity::class.java)
                Log.d(TAG, "#### MyViewFrag____onstart___putEtra ${info.EngSurvey}")
                intent.putExtra("info", info!!)
                startActivity(intent)
            }
        }

        // Fetch User Info
        CoroutineScope(Dispatchers.Main).launch {
            val myInfo = CoroutineScope(Dispatchers.IO).async {
                fetchInfoData()
            }.await()

            infoIcon.setOnClickListener {
                val intent = Intent(context, MyViewInfoActivity::class.java)
                intent.putExtra("info", info!!)
                startActivity(intent)
            }
        }

        // Set UI with userModel
        if(userModel.currentUser.uid != null) {
            userName.text = "${userModel.currentUser.name}님"
            val rewardFin = (userModel.currentUser.rewardTotal!!) - (userModel.currentUser.rewardCurrent!!)
            userRewardFinAmount.text = "${rewardFin}원"
            userRewardYetAmount.text = "${userModel.currentUser.rewardCurrent}원"
            userSurveyCountAmount.text = "${userModel.currentUser.UserSurveyList!!.size}개"
        }


        // Initiate Room DB
        noticeDB = Room.databaseBuilder(
            context!!,
            NoticeDatabase::class.java, "NoticeDatabase"
        ).allowMainThreadQueries().build()

        noticeNum_room = noticeDB.noticeDao().getNum()

        CoroutineScope(Dispatchers.Main).launch {
            val notice = CoroutineScope(Dispatchers.IO).async {
                fetchNoticeNum(noticeDot)
            }.await()

            noticeBtn.setOnClickListener {
                val intent = Intent(context, MyViewNoticeListActivity::class.java)
                intent.putExtra("noticeDiff", noticeNum_fb - noticeNum_room)
                intent.putExtra("notice_room", noticeNum_room)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_myview, container, false)
        val userModel by activityViewModels<CurrentUserViewModel>()

        val noticeBtn = view.findViewById<ImageView>(R.id.MyView_NoticeIcon)
        val noticeDot = view.findViewById<ImageView>(R.id.MyView_NoticeIcon_dot)
        val historyIcon = view.findViewById<LinearLayout>(R.id.MyView_HistoryIcon)
        val infoIcon = view.findViewById<LinearLayout>(R.id.MyView_InfoIcon)
        val settingIcon = view.findViewById<LinearLayout>(R.id.MyView_SettingIcon)
        val contactIcon = view.findViewById<LinearLayout>(R.id.MyView_ContactIcon)

        val userName = view.findViewById<TextView>(R.id.MyView_UserName)
        val userRewardFinAmount = view.findViewById<TextView>(R.id.MyView_UserRewardFinAmount)
        val userRewardYetAmount = view.findViewById<TextView>(R.id.MyView_UserRewardYetAmount)
        val userSurveyCountAmount = view.findViewById<TextView>(R.id.MyView_UserSurveyCountAmount)

        userName.setOnClickListener{
            noticeDB.noticeDao().deleteAll()
        }


        // Initiate Room DB
        noticeDB = Room.databaseBuilder(
            context!!,
            NoticeDatabase::class.java, "NoticeDatabase"
        ).allowMainThreadQueries().build()

        noticeNum_room = noticeDB.noticeDao().getLastID()

        CoroutineScope(Dispatchers.Main).launch {
            val notice = CoroutineScope(Dispatchers.IO).async {
                fetchNoticeNum(noticeDot)
            }.await()

            noticeBtn.setOnClickListener {
                val intent = Intent(context, MyViewNoticeListActivity::class.java)
                intent.putExtra("noticeDiff", noticeNum_fb - noticeNum_room)
                intent.putExtra("notice_room", noticeNum_room)
                startActivity(intent)
            }
        }




        historyIcon.setOnClickListener {
            val intent = Intent(context, MyViewHistoryActivity::class.java)
            startActivity(intent)
        }

        settingIcon.setOnClickListener {
            val intent = Intent(context, MyViewSettingActivity::class.java)
            intent.putExtra("reward_current", userModel.currentUser.rewardCurrent)
            startActivity(intent)

            // [Amplitude] Settings View Showed
            val client = Amplitude.getInstance()
            client.logEvent("Settings View Showed")

        }

        contactIcon.setOnClickListener {
            val intent = Intent(context, MyViewContactActivity::class.java)
            startActivity(intent)
        }

            return view
        }


    // Fetch info of current User for MyViewInfo
    private fun fetchInfoData() {
        val docRef = db.collection("panelData").document(Firebase.auth.currentUser!!.uid)
        var eng: Boolean = true

        docRef.collection("FirstSurvey").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    eng = document["EngSurvey"] as Boolean

                    docRef.get().addOnSuccessListener { document ->
                        if (document != null) {
                            val infoData: InfoData = InfoData(
                                document["name"] as String,
                                document["birthDate"] as String,
                                document["gender"] as String,
                                document["email"] as String,
                                document["phoneNumber"] as String,
                                document["accountType"] as String,
                                document["accountNumber"] as String,
                                eng
                            )
                            info = infoData
                        }
                    }

                }
            }
    }


    // Fetch Notice Num from Firestore
    private fun fetchNoticeNum(dot: ImageView) {
        val docRef = db.collection("lastID").document("lastNoticeID")
        docRef.get().addOnSuccessListener { document ->
            noticeNum_fb = Integer.parseInt(document["lastNoticeID"].toString()) - 1

            if(noticeNum_fb > noticeNum_room) dot.visibility = View.VISIBLE
            else dot.visibility = View.INVISIBLE
            Log.d(TAG, "@@@@@@@@@@@@$noticeNum_fb~~~~~~~~$noticeNum_room~~~~~~~~~")
        }

    }

    private fun fetchNoticeNum2(dot: ImageView) {
        noticeNum_fb = 0
        val docRef = db.collection("AppNotice")
        docRef.get().addOnSuccessListener { documents ->
            for(document in documents) {
                noticeNum_fb++
            }

            if(noticeNum_fb > noticeNum_room) dot.visibility = View.VISIBLE
            else dot.visibility = View.INVISIBLE
        }

    }


}
