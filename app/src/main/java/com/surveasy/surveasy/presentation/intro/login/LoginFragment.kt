package com.surveasy.surveasy.presentation.intro.login

import android.content.Intent
import android.util.Log
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

                    is LoginEvents.NavigateToRegister -> {
                        loginKakao()
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToRegisterAgreeFragment()
                        )
                    }

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

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Log.d("TEST", "$token, $error")
        if (error != null) {
            Log.d("TEST", "fail, $error")
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.d("TEST", "사용자 정보 요청 실패", error)
                }
                else if (user != null) {
                    Log.d("TEST", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                }
            }
            Log.d("TEST", "success, $token")
        }
    }

    private fun loginKakao() {
        lifecycleScope.launch {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                    Log.d("TEST", "first")
                    if (error != null) {
                        Log.d("TEST", "fail, $error")
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        UserApiClient.instance.loginWithKakaoAccount(
                            requireContext(),
                            callback = kakaoLoginCallback
                        )
                    } else if (token != null) {
                        Log.d("TEST", "success, $token")
                    }
                }
            } else {
                Log.d("TEST", "second")
                UserApiClient.instance.loginWithKakaoAccount(
                    requireContext(),
                    callback = kakaoLoginCallback
                )
            }
        }
    }

}