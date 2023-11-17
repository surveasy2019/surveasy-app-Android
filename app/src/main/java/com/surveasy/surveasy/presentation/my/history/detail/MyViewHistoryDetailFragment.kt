package com.surveasy.surveasy.my.history.detail

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.surveasy.surveasy.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewHistoryDetailFragment : Fragment() {
    val storage = Firebase.storage
    val model by activityViewModels<MyViewHistoryDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_view_hisotry_detail, container, false)
        val lastImg = view.findViewById<ImageView>(R.id.historyDetailLastCapture)
        val alert = view.findViewById<TextView>(R.id.historyDetailAlert)
        val alert2 = view.findViewById<TextView>(R.id.historyDetailAlert2)
        val uploadBtn = view.findViewById<Button>(R.id.historyDetailUploadBtn)
        val noneBtn = view.findViewById<Button>(R.id.historyDetailNoneBtn)
        val detailTitle = view.findViewById<TextView>(R.id.historyDetailTitle)
        val detailDate = view.findViewById<TextView>(R.id.historyDetailDate)
        val detailReward = view.findViewById<TextView>(R.id.historyDetailReward)

        CoroutineScope(Dispatchers.Main).launch {
            fetchModel()

            if (model.detailModel[0].title.length > 15) {
                detailTitle.text =
                    model.detailModel[0].title.substring(0, 15) + "..."
            } else {
                detailTitle.text = model.detailModel[0].title
            }
            detailReward.text = model.detailModel[0].reward.toString() + "원"
            detailDate.text = "참여일자 : ${model.detailModel[0].date}"

            if (model.progress[0].progress < 3) {
                fetchLastImg(model.detailModel[0].id, model.filePath[0].filePath)
            } else {
                lastImg.visibility = View.GONE
                alert.visibility = View.GONE
                alert2.visibility = View.VISIBLE
                uploadBtn.visibility = View.GONE
                noneBtn.visibility = View.VISIBLE
            }

            uploadBtn.setOnClickListener {
                if (checkPermission()) {
                    if (model.progress[0].progress < 3) {
                        val intent = Intent(context, MyViewUpdatePhotoActivity::class.java)
                        intent.putExtra("filePath", model.filePath[0].filePath)
                        //storage 폴더 접근 위해
                        intent.putExtra("id", model.detailModel[0].id)
                        intent.putExtra("idChecked", model.detailModel[0].lastId)
                        startActivity(intent)
                        (activity as MyViewHistoryDetailActivity).activityFinish()
                        //(activity as MyViewHistoryActivity).finishActivity()
                    }

                } else {
                    showDialogToGetPermission()
                }
            }

        }


        return view
    }

    private fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("권한이 필요합니다").setMessage("설문 완료 인증 캡쳐본을 수정하기 위해서 접근 권한이 필요합니다.")
        builder.setPositiveButton("설정으로 이동") { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context?.packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            dialogInterface.cancel()
        }
        builder.setNegativeButton("나중에 하기") { dialogInterface, i ->
            Toast.makeText(context, "권한을 허용하지 않을 경우, 설문 완료 캡쳐본 수정이 불가합니다.", Toast.LENGTH_LONG)
                .show()
            dialogInterface.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }


    //upload 버튼 누를 때 permission 상태 확인
    private fun checkPermission(): Boolean {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        return when (PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 승인되어 있는지를 확인
            ContextCompat.checkSelfPermission(
                requireActivity(),
                permissions
            ) -> true

            else -> false
        }
    }

    private suspend fun fetchModel() {
        withContext(Dispatchers.IO) {
            while (model.detailModel.size == 0 || model.progress.size == 0 || model.filePath.size == 0) {
            }
        }
        //Log.d(TAG, "모델 개수 : ${model.filePath}")
    }

    // 기존에 첨부한 이미지 보여주기
    private fun fetchLastImg(id: Int, filePath: String?) {


//        val storageRef: StorageReference = storage.reference.child("historyTest")
        val storageRef: StorageReference = storage.reference.child(id.toString())
//        val file1: StorageReference = storageRef.child("surveytips2image(3).png")
        val file1: StorageReference = storageRef.child(filePath.toString())
        val lastImg = requireView().findViewById<ImageView>(R.id.historyDetailLastCapture)
        val alert = requireView().findViewById<TextView>(R.id.historyDetailAlert)
        val alert2 = requireView().findViewById<TextView>(R.id.historyDetailAlert2)
        val uploadBtn = requireView().findViewById<Button>(R.id.historyDetailUploadBtn)
        val noneBtn = requireView().findViewById<Button>(R.id.historyDetailNoneBtn)

        Glide.with(this).load(R.raw.app_loading).into(lastImg)

        file1.downloadUrl.addOnSuccessListener { item ->
            Glide.with(this).load(item).into(lastImg)
        }.addOnFailureListener {
            lastImg.visibility = View.GONE
            alert.visibility = View.GONE
            alert2.visibility = View.VISIBLE
            uploadBtn.visibility = View.GONE
            noneBtn.visibility = View.VISIBLE
        }
    }

}