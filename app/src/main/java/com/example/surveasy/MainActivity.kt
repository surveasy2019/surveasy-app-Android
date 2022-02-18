package com.example.surveasy

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.surveasy.databinding.ActivityMainBinding
import com.example.surveasy.home.HomeFragment
import com.example.surveasy.home.HomeListViewModel
import com.example.surveasy.list.*
import com.example.surveasy.list.firstsurvey.FirstSurveyListActivity
import com.example.surveasy.login.CurrentUser
import com.example.surveasy.login.CurrentUserViewModel
import com.example.surveasy.my.MyViewFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val surveyList = arrayListOf<SurveyItems>()
    val model by viewModels<SurveyInfoViewModel>()
    val userModel by viewModels<CurrentUserViewModel>()


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_Surveasy)
        binding = ActivityMainBinding.inflate(layoutInflater)


        // Current User
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = user.uid
            val email = user.email
            Log.d(TAG, "@@@@@ Firebase auth email: ${user.email}")
        }

        if(Firebase.auth.currentUser != null) {
            fetchCurrentUser(Firebase.auth.currentUser!!.uid)
            fetchSurvey()
        }


        // Current User from LoginActivity
        val currentUser = intent.getParcelableExtra<CurrentUser>("currentUser_login")
        if(currentUser != null ) {
            userModel.currentUser = currentUser!!
        }
        Log.d(TAG, "###### from Login model: ${userModel.currentUser.email}")


        // Determine Fragment of MainActivity
        val transaction = supportFragmentManager.beginTransaction()
        var defaultFrag_list = false

        defaultFrag_list = intent.getBooleanExtra("defaultFragment_list", false)
        if(defaultFrag_list) {
            setContentView(binding.root)
            transaction.add(R.id.MainView, SurveyListFragment()).commit()
            defaultFrag_list = !defaultFrag_list
        }
        else {
            setContentView(binding.root)
            transaction.add(R.id.MainView, HomeFragment()).commit()
        }


        binding.NavHome.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainView, HomeFragment())
                .commit()
        }


        binding.NavList.setOnClickListener {
            if (userModel.currentUser.didFirstSurvey == false) {
                // Send Current User to Activities
                val intent_surveylistfirstsurvey: Intent = Intent(this, FirstSurveyListActivity::class.java)
                intent_surveylistfirstsurvey.putExtra("currentUser_main", userModel.currentUser)
                startActivity(intent_surveylistfirstsurvey)
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.MainView, SurveyListFragment())
                    .commit()
            }
        }

        binding.NavMy.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainView, MyViewFragment())
                .commit()
        }


//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash",keyHash)


        fun clickList() {
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainView, SurveyListFragment())
                .commit()
        }

    }
    fun clickList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.MainView, SurveyListFragment())
            .commit()}

    private fun fetchCurrentUser(uid: String) :CurrentUser {

        val docRef = db.collection("AndroidUser").document(uid)

        val userSurveyList = ArrayList<UserSurveyItem>()

        docRef.collection("UserSurveyList").get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    var item : UserSurveyItem = UserSurveyItem(
                        document["id"] as String,
                        document["title"] as String?,
                        Integer.parseInt(document["reward"]?.toString()),
                        document["responseDate"] as String?,
                        document["isSent"] as Boolean
                    )
                    userSurveyList.add(item)

                }
            }


        docRef.get().addOnCompleteListener { snapshot ->
            if(snapshot != null) {
                val currentUser : CurrentUser = CurrentUser(
                    snapshot.result["uid"].toString(),
                    snapshot.result["fcmToken"].toString(),
                    snapshot.result["name"].toString(),
                    snapshot.result["email"].toString(),
                    snapshot.result["phoneNumber"].toString(),
                    snapshot.result["gender"].toString(),
                    snapshot.result["birthDate"].toString(),
                    snapshot.result["accountType"].toString(),
                    snapshot.result["accountNumber"].toString(),
                    snapshot.result["accountOwner"].toString(),
                    snapshot.result["inflowPath"].toString(),
                    snapshot.result["didFirstSurvey"] as Boolean?,
                    Integer.parseInt(snapshot.result["reward_total"].toString()),
                    userSurveyList
                )
                userModel.currentUser = currentUser
                Log.d(TAG, "^^^^####$$$$%%%%%%%@@@@@ fetch fun 내부 userModel: ${userModel.currentUser.didFirstSurvey}")

                Log.d(TAG, "@@@@@ fetch fun 내부 userModel: ${userModel.currentUser.UserSurveyList.toString()}")



            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "fail $exception")
        }
        return userModel.currentUser
    }

    private fun fetchSurvey() {
        db.collection("AndroidSurvey")
            // id를 운영진이 올리는 깨끗한 아이디로 설정하면 progress 문제 해결됨.
            .orderBy("id")
            .limit(50).get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val item: SurveyItems = SurveyItems(
                        document["id"] as String,
                        document["title"] as String,
                        document["target"] as String,
                        document["uploadDate"] as String,
                        document["link"] as String,
                        document["spendTime"] as String,
                        document["dueDate"] as String,
                        document["dueTimeTime"] as String,
                        Integer.parseInt(document["reward"].toString()),
                        document["noticeToPanel"] as String?,
                        Integer.parseInt(document["progress"].toString())
                    )
                    if(Integer.parseInt(document["progress"].toString())>1){
                        surveyList.add(item)
                    }
                    Log.d(
                        TAG,
                        "################${document["id"]} and ${document["title"]}"
                    )
                }
                model.surveyInfo.addAll(surveyList)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "fail $exception")
            }
    }
}

