package com.surveasy.surveasy.presentation.intro.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentExistUserBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExistUserFragment : BaseFragment<FragmentExistUserBinding>(R.layout.fragment_exist_user) {
    private val viewModel: ExistUserViewModel by viewModels()
    override fun initView() {
        bind {
            vm = viewModel
        }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is ExistUserEvents.NavigateToMain -> findNavController().toMain()
                }
            }
        }
    }

    override fun initData() {

    }

    private fun NavController.toMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}

