package com.surveasy.surveasy.list

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.amplitude.api.Amplitude
import com.surveasy.surveasy.databinding.ActivitySurveylistdetailBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONException
import org.json.JSONObject
import java.lang.RuntimeException


class SurveyListDetailActivity : AppCompatActivity() {

    val PERMISSION_CODE = 101
    val REQUIRED_PERMISSION = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)

    private lateinit var binding: ActivitySurveylistdetailBinding
    val db = Firebase.firestore
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveylistdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var webView : WebView = binding.surveyWebView
        val url : String = intent.getStringExtra("link")!!
        val id : Int = intent.getIntExtra("id",0)!!
        val idChecked : Int = intent.getIntExtra("idChecked",0)!!
        val index : Int = intent.getIntExtra("index",0)!!
        val reward : Int = intent.getIntExtra("reward",0)
        val title : String = intent.getStringExtra("title")!!
        val title_amplitude = title
        val idChecked_amplitude = idChecked


        // [Amplitude] List Detail Showed
        val client = Amplitude.getInstance()
        val eventProperties = JSONObject()
        try {
            eventProperties.put("id", idChecked).put("title", title)
        } catch (e: JSONException) {
            System.err.println("Invalid JSON")
            e.printStackTrace()
        }
        client.logEvent("List Detail Showed", eventProperties)




        val spannableString = SpannableString(reward.toString()+"원 받으러 가기")
        spannableString.setSpan(UnderlineSpan(),0,spannableString.length,0)
        binding.toolbarUpload.text = spannableString

        if(title.length>15){
            binding.toolbarText.text = title.substring(0,14)+".."
        }else{
            binding.toolbarText.text = title
        }


       //activity 들어가자마자 permission 확인
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show()
        }else{
            requestPermissions(this,REQUIRED_PERMISSION,PERMISSION_CODE)
        }


        //progress 3인 설문이면 alert 창으로 넘기기
        db.collection("surveyData").document(id.toString()).get()
            .addOnSuccessListener { document ->
                if(Integer.parseInt(document["progress"].toString())>2){
                    val intent = Intent(this,SurveyDoneAlertDialogActivity::class.java)
                    startActivity(intent)
                }
            }

        val apply = webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        webView.loadUrl(url)
        webView.pageUp(true)
        val timestamp_start = System.currentTimeMillis() / 1000

        //Toast.makeText(this,"###${id}",Toast.LENGTH_LONG).show()



        binding.toolbarUpload.setOnClickListener {
            val intent = Intent(this, SurveyProofDialogActivity::class.java)
            val title = intent.putExtra("title",title)
            val index = intent.putExtra("index",index)
            val id = intent.putExtra("id",id)
            val idChecked = intent.putExtra("idChecked",idChecked)
            val reward = intent.putExtra("reward", reward)


            val timestamp_end = System.currentTimeMillis() / 1000
            val spentTimeInSurvey = (timestamp_end - timestamp_start).toInt()


            // [Amplitude] Survey Participated
            val client = Amplitude.getInstance()
            val eventProperties = JSONObject()
            try {
                eventProperties.put("title", title_amplitude).put("id", idChecked_amplitude)
                    .put("spentTimeInSurvey", spentTimeInSurvey)
            } catch (e: JSONException) {
                System.err.println("Invalid JSON")
                e.printStackTrace()
            }
            client.logEvent("Survey Participated", eventProperties)




            //permission 없는 상태로 upload 버튼 누르면 설정으로 이동 유도하는 창
            if(checkPermission()){
                startActivityForResult(intent,101)
            }else{
                showDialogToGetPermission()
            }


       }

        //Toolbar
        setSupportActionBar(binding.ToolbarProof)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarProof.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    //뒤로가기 누르면 webView 안에서 적용되게
    override fun onBackPressed() {
        var webView : WebView = binding.surveyWebView
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            finish()
        }
    }

    //permission 동의 여부에 따라
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            PERMISSION_CODE -> {
                if(grantResults.isEmpty()){
                    throw RuntimeException("Empty permission result")
                }
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show()
                } else {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(
                            this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Log.d(TAG,"denied")
                        showDialogToGetPermission()

                    }else{
                        Log.d(TAG,"no more")
                        showDialogToGetPermission()
                    }
                }
            }
        }
    }

    //한번 거부한 적 있으면 그 다음부터는 설정으로 이동하는 intent 나타내기
    private fun showDialogToGetPermission(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("권한이 필요합니다").setMessage("설문 완료 인증 캡쳐본을 전송하기 위해서 접근 권한이 필요합니다.")
        builder.setPositiveButton("설정으로 이동") { dialogInterface, i ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package",packageName,null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton("나중에 하기"){ dialogInterface, i ->
            //Toast.makeText(this,"거부되었습니다",Toast.LENGTH_LONG).show()
        }
        val dialog = builder.create()
        dialog.show()
    }

    //upload 버튼 누를 때 permission 상태 확인
    private fun checkPermission() : Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }















    }

