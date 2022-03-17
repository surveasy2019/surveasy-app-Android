package com.example.surveasy.home.Opinion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.surveasy.MainActivity
import com.example.surveasy.databinding.ActivityHomeopiniondetailBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeOpinionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeopiniondetailBinding
    val db = Firebase.firestore
    var answer : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeopiniondetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // ToolBar
        setSupportActionBar(binding.ToolbarHomeOpinionDetail)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarHomeOpinionDetail.setNavigationOnClickListener {
            onBackPressed()
        }

        val id = intent.getIntExtra("id", 1)
        val question = intent.getStringExtra("question")
        val content1 = intent.getStringExtra("content1")
        val content2 = intent.getStringExtra("content2")

        binding.HomeOpinionDetailQuestion.text = "\"" + question + "\""
        binding.HomeOpinionDetailContent1.text = content1!!.replace("/", "\n")
        binding.HomeOpinionDetailContent2.text = content2!!.replace("/", "\n")




        binding.HomeOpinionFinBtn.setOnClickListener {
            answer = binding.HomeOpinionEditText.text.toString()
            if(answer == null || answer == "") {
                Toast.makeText(this@HomeOpinionDetailActivity, "답변을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                db.collection("AppOpinion").document(id.toString())
                    .update("opinions", FieldValue.arrayUnion(answer))
                    .addOnSuccessListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
            }
        }

    }
}