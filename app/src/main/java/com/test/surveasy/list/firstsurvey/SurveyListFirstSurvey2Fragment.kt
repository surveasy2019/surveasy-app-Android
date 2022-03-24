package com.test.surveasy.list.firstsurvey


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.test.surveasy.R
import com.test.surveasy.login.CurrentUserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class SurveyListFirstSurvey2Fragment() : Fragment() {

    val db = Firebase.firestore
    val userModel by activityViewModels<CurrentUserViewModel>()
    val firstSurveyModel by activityViewModels<FirstSurveyViewModel>()

    private lateinit var citySpinner : Spinner
    private var district : String? = null
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
        citySpinner = view.findViewById(R.id.SurveyListFirstSurvey2_CitySpinner)

        setDistrictSpinner(view)
        setCitySpinner(citySpinner, 0)
        //setPetSpinner(view)
        setHousingTypeSpinner(view)
        setFamilyTypeSpinner(view)

        // pet
        val petRadioGroup = view.findViewById<RadioGroup>(R.id.SurveyListFirstSurvey2_PetRadioGroup)
        petRadioGroup.setOnCheckedChangeListener { petRadioGroup, checked ->
            when(checked) {
                R.id.SurveyListFirstSurvey2_PetDog -> pet = "반려견"
                R.id.SurveyListFirstSurvey2_PetCat -> pet = "반려묘"
                R.id.SurveyListFirstSurvey2_PetEtc -> pet = "기타"
                R.id.SurveyListFirstSurvey2_PetNone -> pet = "없음"
            }
        }

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
//        val familyRadioGroup = view.findViewById<RadioGroup>(R.id.SurveyListFirstSurvey2_FamilyRadioGroup)
//
//        familyRadioGroup.setOnCheckedChangeListener { familyRadioGroup, checked ->
//            when(checked) {
//                R.id.SurveyListFirstSurvey2_Family1 -> family = "1인가구"
//                R.id.SurveyListFirstSurvey2_Family2 -> family = "2인가구"
//                R.id.SurveyListFirstSurvey2_Family3 -> family = "3인가구"
//                R.id.SurveyListFirstSurvey2_Family4 -> family = "4인가구 이상"
//            }
//        }

        val surveyListFirstSurvey2Btn : Button = view.findViewById(R.id.SurveyListFirstSurvey2_Btn)
        surveyListFirstSurvey2Btn.setOnClickListener{
            firstSurveyFin()
        }

        return view
    }

    private fun firstSurveyFin() {
        if(district == "시/도") {
            Toast.makeText(context, "시/도를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(city == "시/군/구") {
            Toast.makeText(context, "시/군/구를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(pet == null) {
            Toast.makeText(context, "반려동물 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(married == null) {
            Toast.makeText(context, "혼인 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(family == null || family == "가구 형태를 선택해주세요") {
            Toast.makeText(context, "가구 형태를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else if(housingType == null || housingType == "주거 형태를 선택해주세요") {
            Toast.makeText(context, "주거 형태를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            firstSurveyModel.firstSurvey.district = district
            firstSurveyModel.firstSurvey.city = city
            firstSurveyModel.firstSurvey.married = married
            firstSurveyModel.firstSurvey.pet = pet
            firstSurveyModel.firstSurvey.family = family
            firstSurveyModel.firstSurvey.housingType = housingType

            firestore()

            val intent : Intent = Intent(context, SurveyListFirstSurveyLast::class.java)
            startActivity(intent)
            (activity as SurveyListFirstSurveyActivity).fin()
        }

    }

    private fun firestore() {

        // update Firestore 'didFirstSurvey (false -> true)"
        db.collection("AndroidUser").document(userModel.currentUser.uid!!)
            .update("didFirstSurvey", true)
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 1. didFirstSurvey field updated!") }
            Log.d(TAG, "***** ${userModel.currentUser.uid}")


        // set Firestore 'userSurveyList"
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR).toString()
        var month = (c.get(Calendar.MONTH) + 1).toString()
        var day = c.get(Calendar.DAY_OF_MONTH).toString()
        if(month.toInt() < 10) month = "0$month"
        if(day.toInt() < 10) day = "0$day"

        val date = year + "-" + month + "-" + day
        val firstSurvey = hashMapOf(
            "lastIDChecked" to "0",
            "isSent" to false,
            "responseDate" to date,
            "panelReward" to 200,
            "title" to "패널 기본 정보 조사"
        )

        db.collection("AndroidUser").document(userModel.currentUser.uid!!)
            .collection("UserSurveyList").document("0")
            .set(firstSurvey)
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 2. UserSurveyLIst updated!") }



        // update Firestore reward
        db.collection("AndroidUser").document(userModel.currentUser.uid!!)
            .update(
                "reward_current", userModel.currentUser.rewardCurrent!! + 200,
                "reward_total", userModel.currentUser.rewardTotal!! + 200
            )
            .addOnSuccessListener { Log.d(TAG, "@@@@@ 3. First Survey Reward updated!") }
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
        db.collection("AndroidUser").document(userModel.currentUser.uid!!)
            .collection("FirstSurvey").document(userModel.currentUser.uid!!)
            .set(FirstSurvey).addOnSuccessListener { Log.d(TAG, "FFFFFFFFFFFFFF First Survey uploaded!") }
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
                if(position == 8) {
                    citySpinner.visibility = View.INVISIBLE
                    city = ""
                }
                else {
                    citySpinner.visibility = View.VISIBLE
                    setCitySpinner(citySpinner, position)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun setCitySpinner(citySpinner: Spinner, districtPosition: Int) {
        var cityList = resources.getStringArray(R.array.서울)

        when(districtPosition) {
            1 -> { cityList = resources.getStringArray(R.array.서울) }
            2 -> { cityList = resources.getStringArray(R.array.부산) }
            3 -> { cityList = resources.getStringArray(R.array.대구) }
            4 -> { cityList = resources.getStringArray(R.array.인천) }
            5 -> { cityList = resources.getStringArray(R.array.광주) }
            6 -> { cityList = resources.getStringArray(R.array.대전) }
            7 -> { cityList = resources.getStringArray(R.array.울산) }
            9 -> { cityList = resources.getStringArray(R.array.경기) }
            10 -> { cityList = resources.getStringArray(R.array.충북) }
            11 -> { cityList = resources.getStringArray(R.array.충남) }
            12 -> { cityList = resources.getStringArray(R.array.전북) }
            13 -> { cityList = resources.getStringArray(R.array.전남) }
            14 -> { cityList = resources.getStringArray(R.array.경북) }
            15 -> { cityList = resources.getStringArray(R.array.경남) }
            16 -> { cityList = resources.getStringArray(R.array.제주) }
        }

        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cityList)
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


//    private fun setPetSpinner(view: View) {
//        val petList = resources.getStringArray(R.array.pet)
//        val petAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, petList)
//        val petSpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_PetSpinner)
//        petSpinner.adapter = petAdapter
//        petSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                pet = petList[position]
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//        }
//    }

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

    private fun setFamilyTypeSpinner(view: View) {
        val familyTypeList = resources.getStringArray(R.array.familyType)
        val familyTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, familyTypeList)
        val familyTypeSpinner = view.findViewById<Spinner>(R.id.SurveyListFirstSurvey2_FamilyTypeSpinner)
        familyTypeSpinner.adapter = familyTypeAdapter
        familyTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                family = familyTypeList[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }




}