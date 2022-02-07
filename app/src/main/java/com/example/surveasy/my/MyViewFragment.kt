package com.example.surveasy.my

import android.content.Intent
import android.os.Bundle
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.surveasy.R
import android.widget.TextView


class MyViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_myview, container, false)

        val settingBtn = view.findViewById<ImageButton>(R.id.MyView_SettingBtn)
        val historyIcon = view.findViewById<ImageButton>(R.id.MyView_HistoryIcon)
        val infoIcon = view.findViewById<ImageButton>(R.id.MyView_InfoIcon)
        val contactIcon = view.findViewById<ImageButton>(R.id.MyView_ContactIcon)
        val inviteIcon = view.findViewById<ImageButton>(R.id.MyView_InviteIcon)
        val noticeIcon = view.findViewById<Button>(R.id.MyView_NoticeMore)
        val myIdText = view.findViewById<TextView>(R.id.MyView_UserName)
        val logoutBtn = view.findViewById<Button>(R.id.MyView_Logout)




        logoutBtn.setOnClickListener {
//            CoroutineScope(Dispatchers.Main).launch {
//                val value = DataApplication.getInstance().getDataStore()?.long?.first()
//                Toast.makeText(context,"value : $value",Toast.LENGTH_LONG).show()
//
//            }
//        }


            settingBtn.setOnClickListener {
                val intent = Intent(context, MyViewSettingActivity::class.java)
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
            contactIcon.setOnClickListener {
                val intent = Intent(context, MyViewContactActivity::class.java)
                startActivity(intent)
            }
            inviteIcon.setOnClickListener {
                val intent = Intent(context, MyViewInviteActivity::class.java)
                startActivity(intent)
            }
            noticeIcon.setOnClickListener {
                val intent = Intent(context, MyViewNoticeListActivity::class.java)
                startActivity(intent)
            }

        }

            return view



        }

}
