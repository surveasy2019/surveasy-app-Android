package com.example.surveasy.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.surveasy.R
import com.example.surveasy.login.LoginActivity
import com.example.surveasy.login.RegisterActivity
import com.kakao.sdk.user.UserApiClient


class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val register: Button = view.findViewById(R.id.HomeToRegister)
        val IdText: TextView = view.findViewById(R.id.Home_GreetingText)




        register.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)


        }

    // 홈에 들어올때마다 불러오는 것 vs 리스트 저장 후?
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                IdText.text = "로그인이 필요합니다"
                Log.d(TAG,"fail")
            } else if (user != null) {
                IdText.text = "안녕하세요, ${ user.kakaoAccount?.profile?.nickname }님!"

                Log.d(
                    TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )


            }
        }




            return view


        }
    }

