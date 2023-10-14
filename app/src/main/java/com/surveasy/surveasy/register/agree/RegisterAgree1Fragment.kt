package com.surveasy.surveasy.register.agree

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.surveasy.surveasy.databinding.FragmentRegisterAgree1Binding
import com.surveasy.surveasy.register.RegisterActivity
import com.surveasy.surveasy.register.RegisterInfo1ViewModel

class RegisterAgree1Fragment : Fragment() {

    val db = Firebase.firestore
    private var _binding : FragmentRegisterAgree1Binding? = null
    private val binding get() = _binding!!
    private val registerModel by activityViewModels<RegisterInfo1ViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAgree1Binding.inflate(layoutInflater)

        initView()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        with(binding) {
            btnNext.setOnClickListener {
                if(cbAgree2.isChecked && cbAgree3.isChecked){
                    (activity as RegisterActivity).goAgree2()
                }else{
                    Toast.makeText(context,"필수 항목에 동의해주세요",Toast.LENGTH_LONG).show()
                }
                if(cbAgree4.isChecked){
                    registerModel.registerInfo1.marketingAgree = true
                }
            }

            tvDetail2.setOnClickListener {
                val intent = Intent(context, RegisterTerm1::class.java)
                startActivity(intent)
            }
            tvDetail3.setOnClickListener {
                val intent = Intent(context, RegisterTerm2::class.java)
                startActivity(intent)
            }



            cbAgree1.setOnClickListener {
                if(cbAgree1.isChecked){
                    cbAgree2.isChecked = true
                    cbAgree3.isChecked = true
                    cbAgree4.isChecked = true
                }else{
                    cbAgree2.isChecked = false
                    cbAgree3.isChecked = false
                    cbAgree4.isChecked = false
                }
                showMarketingText()

            }
            cbAgree2.setOnClickListener {
                checkAgreeAll()
            }

            cbAgree3.setOnClickListener {
                checkAgreeAll()
            }

            cbAgree4.setOnClickListener {
                checkAgreeAll()
                showMarketingText()
            }
        }
    }

    private fun checkAgreeAll() {
        with(binding){
            if(!cbAgree2.isChecked || !cbAgree3.isChecked || !cbAgree4.isChecked){
                cbAgree1.isChecked = false
            }
            if(cbAgree2.isChecked && cbAgree3.isChecked && cbAgree4.isChecked){
                cbAgree1.isChecked = true
            }
        }
    }

    private fun showMarketingText() {
        with(binding) {
            tvSnsText.visibility = if(cbAgree4.isChecked) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }


}