package com.app.surveasy.my.setting

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.surveasy.databinding.ActivityMyviewsettingwithdrawBinding
import com.app.surveasy.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewSettingWithdrawActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyviewsettingwithdrawBinding
    private lateinit var builder : AlertDialog.Builder
    val db = Firebase.firestore
    var checked = arrayOf<String?>(null, null, null, null, null)
    var valid_reason = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyviewsettingwithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ToolBar
        setSupportActionBar(binding.ToolbarMyViewSettingWithdraw)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        binding.ToolbarMyViewSettingWithdraw.setNavigationOnClickListener {
            onBackPressed()
        }


        // reward_current
        val reward_current = intent.getIntExtra("reward_current", 0)
        binding.MyViewSettingWithdrawCurrentReward.text = "현재 정산 예정 금액 ${reward_current}원"



        // Withdraw
        builder = AlertDialog.Builder(this)
        binding.MyViewSettingWithdrawWithdrawBtn.setOnClickListener{
            var pw = binding.MyViewSettingWithdrawPwInput.text.toString()

            // Set CheckboxListeners
            binding.MyViewSettingWithdrawReason1.setOnCheckedChangeListener(CheckboxListener())
            binding.MyViewSettingWithdrawReason2.setOnCheckedChangeListener(CheckboxListener())
            binding.MyViewSettingWithdrawReason3.setOnCheckedChangeListener(CheckboxListener())
            binding.MyViewSettingWithdrawReason4.setOnCheckedChangeListener(CheckboxListener())
            binding.MyViewSettingWithdrawReason5.setOnCheckedChangeListener(CheckboxListener())

            for(i in checked) {
                if(i != null) valid_reason = true
            }

            if(!valid_reason) {
                Toast.makeText(this, "탈퇴 사유를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(pw == "") {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                pwCheck(pw)
            }

        }

    }

    // function to check Validity of Password
    private fun pwCheck(pw: String) {
        val auth = FirebaseAuth.getInstance()
        val email = Firebase.auth.currentUser!!.email
        auth.signInWithEmailAndPassword(email!!, pw!!)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    showDialog()
                }
                else {
                    Toast.makeText(this, "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

    }


    // function to show Withdraw Dialog
    private fun showDialog() {
        builder.setTitle("패널탈퇴하시겠습니까?")
            .setCancelable(true)
            .setPositiveButton("예"){ dialogInterface, it ->
                finish()
                withdraw()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("아니요"){ dialogInterface, it ->
                dialogInterface.cancel()
            }
            .show()
    }


    // function tp withdraw
    private fun withdraw() {

        // Update Firestore [withdrawData]
        val checkedNotNull = checked.filterNotNull()
        val item = hashMapOf(
            "reason" to checkedNotNull
        )
        db.collection("AndroidWithdrawData").document(Firebase.auth.currentUser!!.uid).set(item)
            .addOnSuccessListener { Log.d(TAG, "TTTTTTTTTT ${checkedNotNull}") }


        // Withdraw User
        val user = Firebase.auth.currentUser!!
        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "########User account deleted.")
                }
            }
    }



    // CheckBox Listener
    inner class CheckboxListener: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            when(buttonView?.id) {
                binding.MyViewSettingWithdrawReason1.id ->
                    if(isChecked) checked[0] = binding.MyViewSettingWithdrawReason1.text.toString()
                    else checked[0]  = null
                binding.MyViewSettingWithdrawReason2.id ->
                    if(isChecked) checked[1] = binding.MyViewSettingWithdrawReason2.text.toString()
                    else checked[1]  = null
                binding.MyViewSettingWithdrawReason3.id ->
                    if(isChecked) checked[2] = binding.MyViewSettingWithdrawReason3.text.toString()
                    else checked[2]  = null
                binding.MyViewSettingWithdrawReason4.id ->
                    if(isChecked) checked[3] = binding.MyViewSettingWithdrawReason4.text.toString()
                    else checked[3]  = null
                binding.MyViewSettingWithdrawReason5.id ->
                    if(isChecked) checked[4] = binding.MyViewSettingWithdrawReason5.text.toString()
                    else checked[4]  = null
            }



        }
    }


}