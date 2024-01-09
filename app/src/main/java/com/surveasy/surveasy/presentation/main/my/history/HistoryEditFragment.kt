package com.surveasy.surveasy.presentation.main.my.history

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentHistoryEditBinding
import com.surveasy.surveasy.presentation.base.BaseFragment

class HistoryEditFragment :
    BaseFragment<FragmentHistoryEditBinding>(R.layout.fragment_history_edit) {
    private val viewModel: HistoryViewModel by activityViewModels()
    private var imgUrl: Uri? = null
    override fun initData() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is HistoryEvents.ShowLoading -> showLoading(requireContext())
                    is HistoryEvents.DismissLoading -> {
                        dismissLoading()
                        navigateToHistoryMain()
                    }

                    is HistoryEvents.ShowErrorMsg -> Log.d("TEST", "${it.msg}")
                    else -> Unit
                }
            }
        }
    }

    override fun initView() {
        openOrSetting()
        bind {
            vm = viewModel
            btnPick.setOnClickListener { openOrSetting() }
            btnSubmit.setOnClickListener { uploadToFb() }
        }
    }

    private fun openOrSetting() {
        if (checkPermission()) {
            openGallery()
        } else {
            showDialogToGetPermission()
        }
    }

    private fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("권한이 필요합니다").setMessage("설문 완료 인증 캡쳐본을 전송하기 위해서 접근 권한이 필요합니다.")
        builder.setPositiveButton("설정으로 이동") { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireContext().packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            dialogInterface.cancel()
        }
        builder.setNegativeButton("나중에 하기") { dialogInterface, i ->
            Toast.makeText(
                requireContext(),
                "권한을 허용하지 않을 경우, 설문 완료 캡쳐본 전송이 불가합니다.",
                Toast.LENGTH_LONG
            ).show()
            dialogInterface.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }


    private fun checkPermission(): Boolean {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        return when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permissions
            ) -> true

            else -> false
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data

                uri?.let {
                    binding.ivPickImg.setImageURI(it)
                    imgUrl = it
                }
            }
        }

    private fun uploadToFb() {
        repeatOnStarted {
            imgUrl ?: return@repeatOnStarted
            viewModel.editResponse(imgUrl.toString(), 0, "edit3")
        }
    }

    private fun navigateToHistoryMain() {
        val intent = Intent(context, HistoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}