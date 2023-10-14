package com.surveasy.surveasy.register.agree

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterAgree2Binding
import com.surveasy.surveasy.register.RegisterActivity

class RegisterAgree2Fragment : Fragment() {
    private var _binding : FragmentRegisterAgree2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterAgree2Binding.inflate(layoutInflater)

//        val sen = "불성실한 응답 적발 시, 패널 활동이 강제중지 되며 응답비는 지급되지 않습니다."
//
//        val spannableString = SpannableString(sen)
//        spannableString.setSpan(ForegroundColorSpan(Color.RED),21,26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        binding.tvAgreeContent3.text = spannableString


        binding.btnCheck.setOnClickListener {
            (activity as RegisterActivity).goToRegister1()
        }

        return binding.root
    }
}