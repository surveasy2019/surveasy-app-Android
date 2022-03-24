package com.test.surveasy.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.surveasy.databinding.ActivitySurveyListDetailDialogBinding

class SurveyListDetailDialogActivity : AppCompatActivity() {

    lateinit var binding : ActivitySurveyListDetailDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyListDetailDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailList = intent.getParcelableExtra<SurveyItems>("detailList")

        binding.SurveyDetailDialogDetailText.text =
            "설문 대상 : ${detailList?.target} \n설문 소요 시간 : ${detailList?.spendTime} \n" +
                    "설문 적립금 : ${detailList?.reward} \n"


        binding.SurveyDetailDialogCloseBtn.setOnClickListener {
            finish()
        }
    }
}