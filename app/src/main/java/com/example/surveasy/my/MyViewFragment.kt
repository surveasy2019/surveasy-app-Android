package com.example.surveasy.my


import android.os.Bundle
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.surveasy.R
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.surveasy.login.CurrentUserViewModel
import com.example.surveasy.my.history.MyViewHistoryActivity
import com.google.firebase.auth.ktx.auth
import com.example.surveasy.my.info.MyViewInfoActivity
import com.example.surveasy.my.notice.MyViewNoticeListActivity
import com.example.surveasy.my.setting.MyViewSettingActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MyViewFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_myview, container, false)
        val userModel by activityViewModels<CurrentUserViewModel>()

        val noticeBtn = view.findViewById<ImageButton>(R.id.MyView_NoticeIcon)
        val historyIcon = view.findViewById<Button>(R.id.MyView_HistoryIcon)
        val infoIcon = view.findViewById<Button>(R.id.MyView_InfoIcon)
        val settingIcon = view.findViewById<Button>(R.id.MyView_SettingIcon)
        val contactIcon = view.findViewById<Button>(R.id.MyView_ContactIcon)

        val userName = view.findViewById<TextView>(R.id.MyView_UserName)
        val userRewardAmount = view.findViewById<TextView>(R.id.MyView_UserRewardAmount)


        if(userModel.currentUser.uid != null) {
            userName.text = "${userModel.currentUser.name}님"
            userRewardAmount.text = "$ ${userModel.currentUser.rewardTotal}"
        }


        noticeBtn.setOnClickListener {
            val intent = Intent(context, MyViewNoticeListActivity::class.java)
            startActivity(intent)
        }
        historyIcon.setOnClickListener {
            val intent = Intent(context, MyViewHistoryActivity::class.java)
            startActivity(intent)
        }
        infoIcon.setOnClickListener {
            val intent = Intent(context, MyViewInfoActivity::class.java)
            startActivity(intent)
        }
        settingIcon.setOnClickListener {
            val intent = Intent(context, MyViewSettingActivity::class.java)
            startActivity(intent)
        }
        contactIcon.setOnClickListener {
            val intent = Intent(context, MyViewContactActivity::class.java)
            startActivity(intent)
        }





            return view


        }

}
