package com.surveasy.surveasy.presentation.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentRegisterInput2Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import com.surveasy.surveasy.presentation.main.MainActivity

class RegisterInput2Fragment :
    BaseFragment<FragmentRegisterInput2Binding>(FragmentRegisterInput2Binding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initBankSpinner()
    }

    private fun initView(view: View) {
        navController = Navigation.findNavController(view)
        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            navController = Navigation.findNavController(view)

            repeatOnStarted {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvents.NavigateToBack ->
                            navController.navigateUp()
                        is RegisterEvents.NavigateToMain -> navigateToMain()
                    }
                }
            }

        }
    }

    private fun initBankSpinner() {
        val bankList = resources.getStringArray(R.array.accountType)
        val bankAdapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, bankList)

        bind {
            sBank.apply {
                adapter = bankAdapter
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.setBank(bankList[position])
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            }
        }
    }

    private fun navigateToMain(){
        val intent = Intent(context, MainActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}