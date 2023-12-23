package com.surveasy.surveasy.presentation.main.my.edit

import android.os.Bundle
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.ActivityEditBinding
import com.surveasy.surveasy.presentation.base.BaseActivity

class EditActivity : BaseActivity<ActivityEditBinding>(ActivityEditBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }
}