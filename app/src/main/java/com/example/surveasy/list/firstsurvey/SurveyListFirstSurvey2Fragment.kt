package com.example.surveasy.list.firstsurvey


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.surveasy.MainActivity
import com.example.surveasy.R
import com.example.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.protobuf.LazyStringArrayList


class SurveyListFirstSurvey2Fragment() : Fragment() {

    val db = Firebase.firestore
    val firstSurveyModel by activityViewModels<FirstSurveyViewModel>()

    private lateinit var district : String
    private var city : String? = null
    private var pet : String? = null
    private var housingType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylistfirstsurvey2,container,false)
        val userModel by activityViewModels<CurrentUserViewModel>()

        setDistrictSpinner(view)
        setPetSpinner(view)
        setHousingTypeSpinner(view)

        val surveyListFirstSurvey2Btn : Button = view.findViewById(R.id.SurveyListFirstSurvey2_Btn)
        surveyListFirstSurvey2Btn.setOnClickListener{

            // update Firestore 'firstSurvey"
            db.collection("AndroidUser").document(userModel.currentUser.uid!!)
                .update("didFirstSurvey", true)
                .addOnSuccessListener { Log.d(TAG, "@@@@@ First Survey field updated!") }
            Log.d(TAG, "***** ${userModel.currentUser.uid}")



            val intent_main : Intent = Intent(context, MainActivity::class.java)
            intent_main.putExtra("defaultFragment_list", true)
            startActivity(intent_main)

        }

        return view
    }

    private fun setDistrictSpinner(view: View) {
        val districtList = resources.getStringArray(R.array.district)
        val districtAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, districtList)
        val districtSpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_DistrictSpinner)
        districtSpinner.adapter = districtAdapter
        districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                district = districtList[position]
                setCitySpinner(view!!, district)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun setCitySpinner(view: View, district: String) {
        var spinnerName: String? = null
        spinnerName = "SurveyListFirstSurvey2_CitySpinner_" + district
        val cityList = resources.getStringArray(R.array.서울)

        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cityList)
        val citySpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_CitySpinner_서울)
        citySpinner.adapter = cityAdapter
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = cityList[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun setPetSpinner(view: View) {
        val petList = resources.getStringArray(R.array.pet)
        val petAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, petList)
        val petSpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_PetSpinner)
        petSpinner.adapter = petAdapter
        petSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0) {
                    pet = petList[position]
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setHousingTypeSpinner(view: View) {
        val housingTypeList = resources.getStringArray(R.array.housingType)
        val housingTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, housingTypeList)
        val housingTypeSpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_HousingTypeSpinner)
        housingTypeSpinner.adapter = housingTypeAdapter
        housingTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0) {
                    housingType = housingTypeList[position]
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }




}