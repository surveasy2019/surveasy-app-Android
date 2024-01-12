package com.surveasy.surveasy.presentation.intro.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentLoginBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()
    override fun initView() = with(binding) {
        vm = viewModel
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {

                    is LoginEvents.NavigateToRegister -> findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToRegisterAgreeFragment()
                    )

                    is LoginEvents.NavigateToMain -> findNavController().toMain()
                    is LoginEvents.ShowSnackBar -> showSnackBar(event.msg)

                    else -> Unit
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