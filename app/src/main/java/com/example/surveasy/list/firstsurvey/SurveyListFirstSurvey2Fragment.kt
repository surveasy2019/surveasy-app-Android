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
    val userModel by activityViewModels<CurrentUserViewModel>()
    val firstSurveyModel by activityViewModels<FirstSurveyViewModel>()

    private lateinit var district : String
    private var city : String? = null
    private var married: String? = null
    private var pet : String? = null
    private var family : String? = null
    private var housingType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_surveylistfirstsurvey2,container,false)


        setDistrictSpinner(view)
        setPetSpinner(view)
        setHousingTypeSpinner(view)

        // married
        val marriedRadioGroup = view.findViewById<RadioGroup>(R.id.SurveyListFirstSurvey2_MarriedRadioGroup)
        marriedRadioGroup.setOnCheckedChangeListener { marriedRadioGroup, checked ->
            when(checked) {
                R.id.SurveyListFirstSurvey2_MarriedYet -> married = "미혼"
                R.id.SurveyListFirstSurvey2_Married -> married = "기혼"
                R.id.SurveyListFirstSurvey2_MarriedDivorce -> married = "이혼"
            }
        }

        // family
        val familyRadioGroup = view.findViewById<RadioGroup>(R.id.SurveyListFirstSurvey2_FamilyRadioGroup)
        familyRadioGroup.setOnCheckedChangeListener { familyRadioGroup, checked ->
            when(checked) {
                R.id.SurveyListFirstSurvey2_Family1 -> family = "1인가구"
                R.id.SurveyListFirstSurvey2_Family2 -> family = "2인가구"
                R.id.SurveyListFirstSurvey2_Family3 -> family = "3인가구"
                R.id.SurveyListFirstSurvey2_Family4 -> family = "4인가구 이상"
            }
        }

        val surveyListFirstSurvey2Btn : Button = view.findViewById(R.id.SurveyListFirstSurvey2_Btn)
        surveyListFirstSurvey2Btn.setOnClickListener{
            firstSurveyFin()
        }

        return view
    }

    private fun firstSurveyFin() {
        firstSurveyModel.firstSurvey.district = district
        firstSurveyModel.firstSurvey.city = city
        firstSurveyModel.firstSurvey.married = married
        firstSurveyModel.firstSurvey.pet = pet
        firstSurveyModel.firstSurvey.family = family
        firstSurveyModel.firstSurvey.housingType = housingType

        firestore()

        val intent_main : Intent = Intent(context, MainActivity::class.java)
        intent_main.putExtra("defaultFragment_list", true)
        startActivity(intent_main)
    }

    private fun firestore() {

        // update Firestore 'didFirstSurvey (false -> true)"
        db.collection("AndroidUser").document(userModel.currentUser.uid!!)
            .update("didFirstSurvey", true)
            .addOnSuccessListener { Log.d(TAG, "@@@@@ didFirstSurvey field updated!") }
            Log.d(TAG, "***** ${userModel.currentUser.uid}")


        // add "FirstSurvey" collection to panelData
        val FirstSurvey = hashMapOf(
            "job" to firstSurveyModel.firstSurvey.job,
            "major" to firstSurveyModel.firstSurvey.major,
            "university" to firstSurveyModel.firstSurvey.university,
            "EngSurvey" to firstSurveyModel.firstSurvey.EngSurvey,
            "military" to firstSurveyModel.firstSurvey.military,
            "district" to firstSurveyModel.firstSurvey.district,
            "city" to firstSurveyModel.firstSurvey.city,
            "married" to firstSurveyModel.firstSurvey.married,
            "pet" to firstSurveyModel.firstSurvey.pet,
            "family" to firstSurveyModel.firstSurvey.family,
            "housingType" to firstSurveyModel.firstSurvey.housingType
        )
        db.collection("AndroidUser").document(userModel.currentUser.uid!!).collection("FirstSurvey")
            .add(FirstSurvey).addOnSuccessListener { Log.d(TAG, "FFFFFFFFFFFFFF First Survey uploaded!") }
    }


    private fun setDistrictSpinner(view: View) {
        val districtList = resources.getStringArray(R.array.district)
        val districtAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, districtList)
        val districtSpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_DistrictSpinner)
        val citySpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_CitySpinner)

        districtSpinner.adapter = districtAdapter
        districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                district = districtList[position]
                if(position != 7) {
                    // setCitySpinner(view!!, position)
                    citySpinner.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun setCitySpinner(view: View, district_position: Int) {
        var cityList = resources.getStringArray(R.array.서울)

        when(district_position) {
            0 -> { cityList = resources.getStringArray(R.array.서울) }
            1 -> { cityList = resources.getStringArray(R.array.부산) }
            2 -> { cityList = resources.getStringArray(R.array.대구) }
            3 -> { cityList = resources.getStringArray(R.array.인천) }
            4 -> { cityList = resources.getStringArray(R.array.광주) }
            5 -> { cityList = resources.getStringArray(R.array.대전) }
            6 -> { cityList = resources.getStringArray(R.array.울산) }
            8 -> { cityList = resources.getStringArray(R.array.경기) }
            9 -> { cityList = resources.getStringArray(R.array.충북) }
            10 -> { cityList = resources.getStringArray(R.array.충남) }
            11 -> { cityList = resources.getStringArray(R.array.전북) }
            12 -> { cityList = resources.getStringArray(R.array.전남) }
            13 -> { cityList = resources.getStringArray(R.array.경북) }
            14 -> { cityList = resources.getStringArray(R.array.경남) }
            15 -> { cityList = resources.getStringArray(R.array.제주) }
        }

        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cityList)
        val citySpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_CitySpinner)
        citySpinner.adapter = cityAdapter
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = cityList[position]
                Log.d(TAG, "***** $city")
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
                housingType = housingTypeList[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }




}