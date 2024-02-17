package com.surveasy.surveasy.presentation.intro.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentLoginBinding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainActivity
import com.surveasy.surveasy.presentation.util.ErrorMsg.GET_INFO_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.SIGNUP_ERROR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()
    override fun initView() = with(binding) {
        checkAutoLogin()
        vm = viewModel
        btnExist.setOnClickListener { repeatOnStarted { viewModel.createExistPanel() } }
    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginEvents.ClickKakaoSignup -> loginKakao()
                    is LoginEvents.NavigateToRegister -> findNavController().toRegister()
                    is LoginEvents.NavigateToMain -> findNavController().toMain()
                    is LoginEvents.ShowSnackBar -> showSnackBar(event.msg)
                    is LoginEvents.ShowLoading -> showLoading(requireContext())
                    is LoginEvents.DismissLoading -> dismissLoading()
                    else -> Unit
                }
            }
        }
    }

    override fun initData() {

    }

    private fun checkAutoLogin() {
        binding.cbAutoLogin.setOnCheckedChangeListener { buttonView, _ ->
            viewModel.setAutoLogin(buttonView.isChecked)
        }
    }

    private fun NavController.toMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun NavController.toRegister() {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegisterAgreeFragment())
    }

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            showSnackBar(SIGNUP_ERROR)
        } else if (token != null) {
            kakaoInfoCallback()
        }
    }

    private val kakaoInfoCallback: () -> Unit = {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                showSnackBar(GET_INFO_ERROR)
            } else if (user != null) {
                with(user) {
                    viewModel.kakaoSignup(
                        kakaoAccount?.name,
                        kakaoAccount?.email,
                        kakaoAccount?.phoneNumber,
                        kakaoAccount?.gender,
                        kakaoAccount?.birthyear,
                        kakaoAccount?.birthday
                    )
                }
            }
        }
    }

    private fun loginKakao() {
        lifecycleScope.launch {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                    if (error != null) {
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        UserApiClient.instance.loginWithKakaoAccount(
                            requireContext(),
                            callback = kakaoLoginCallback
                        )
                    } else if (token != null) {
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                showSnackBar(GET_INFO_ERROR)
                            } else if (user != null) {
                                with(user) {
                                    viewModel.kakaoSignup(
                                        kakaoAccount?.name,
                                        kakaoAccount?.email,
                                        kakaoAccount?.phoneNumber,
                                        kakaoAccount?.gender,
                                        kakaoAccount?.birthyear,
                                        kakaoAccount?.birthday
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    requireContext(),
                    callback = kakaoLoginCallback
                )
            }
        }
    }

}