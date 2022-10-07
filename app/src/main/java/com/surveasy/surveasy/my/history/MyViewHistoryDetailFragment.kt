package com.surveasy.surveasy.my.history

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import org.w3c.dom.Text

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
            Log.d(TAG, "onCreateView: 시작")
            fetchModel()
            Log.d(TAG, "onCreateView: 끝")

            if(model.detailModel[0].title.length>18){
                detailTitle.text =
                    model.detailModel[0].title.substring(0,19)+"\n"+model.detailModel[0].title.substring(19,36).trim()+"..."
            }else{
                detailTitle.text = model.detailModel[0].title
            }
            detailReward.text = model.detailModel[0].reward.toString()+"원"
            detailDate.text = "참여일자 : ${model.detailModel[0].date}"

            if(model.progress[0].progress<3){
                fetchLastImg(model.detailModel[0].id, model.detailModel[0].filePath)
            }else{
                Log.d(TAG, "onCreateView: 프로그래스 ${model.progress[0].progress}")
                lastImg.visibility = View.GONE
                alert.visibility = View.GONE
                alert2.visibility = View.VISIBLE
                uploadBtn.visibility = View.GONE
                noneBtn.visibility = View.VISIBLE
            }

            uploadBtn.setOnClickListener {
                if(model.progress[0].progress<3){
                    val intent = Intent(context, MyViewUpdatePhotoActivity::class.java)
                    intent.putExtra("filePath", model.detailModel[0].filePath)
                    //storage 폴더 접근 위해
                    intent.putExtra("id", model.detailModel[0].id)
                    intent.putExtra("idChecked", model.detailModel[0].lastId)
                    startActivity(intent)
                    (activity as MyViewHistoryDetailActivity).activityFinish()
                }else{
                    Toast.makeText(context,"마감된 설문은 완료 화면 변경이 불가합니다.", Toast.LENGTH_LONG).show()
                }
            }

        }


        return view
    }

    private suspend fun fetchModel(){
        withContext(Dispatchers.IO){
            while (model.detailModel.size==0 || model.progress.size==0){ }
        }
    }

    // 기존에 첨부한 이미지 보여주기
    private fun fetchLastImg(id : Int, filePath : String?) {


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
            Log.d(ContentValues.TAG, "fetchLastImg: $item")
            Glide.with(this).load(item).into(lastImg)
        }.addOnFailureListener{
            Log.d(ContentValues.TAG, "fetchLastImg: fail###")
            lastImg.visibility = View.GONE
            alert.visibility = View.GONE
            alert2.visibility = View.VISIBLE
            uploadBtn.visibility = View.GONE
            noneBtn.visibility = View.VISIBLE
        }
    }

}