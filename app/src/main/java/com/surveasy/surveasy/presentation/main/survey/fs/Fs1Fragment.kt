package com.surveasy.surveasy.presentation.main.survey.fs

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentFs1Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class Fs1Fragment : BaseFragment<FragmentFs1Binding>(R.layout.fragment_fs1) {
    private val viewModel: FsViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is FsEvents.NavigateToBack -> findNavController().navigateUp()
                    is FsEvents.NavigateToInput2 -> findNavController().toInput2()
                    else -> Unit
                }
            }
        }
        repeatOnStarted {
            viewModel.uiState.collectLatest {
                binding.btnNext.isEnabled =
                    it.jobValid && it.militaryValid && (!it.isStudent || it.majorValid)

                binding.sMajor.visibility = if (it.isStudent) View.VISIBLE else View.GONE
            }
        }


        repeatOnStarted {
            viewModel.english.collectLatest {
                binding.tvEnglish.text =
                    resources.getText(if (it) R.string.my_english_yes else R.string.my_english_no)
            }
        }
    }

    override fun initView() = with(binding) {
        initSpinner()
        militaryRadioListener()
        initEnglishSwitch()
        btnNext.setOnClickListener { viewModel.navigateToNext(FsNavType.TO_INPUT2) }
        ivBack.setOnClickListener { viewModel.navigateToList() }

    }

    private fun militaryRadioListener() {
        binding.rgMilitary.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_military_done -> viewModel.setMilitary(0)
                R.id.rb_military_yet -> viewModel.setMilitary(1)
                R.id.rb_military_no -> viewModel.setMilitary(2)
            }
        }
    }

    private fun NavController.toInput2() {
        navigate(Fs1FragmentDirections.actionFs1FragmentToFs2Fragment())
    }

    private fun initEnglishSwitch() {
        binding.smEnglish.setOnCheckedChangeListener { _, b ->
            viewModel.setEnglish(b)
        }
    }

    private fun initSpinner() = with(binding) {
        sJob.setAdapter(resources.getStringArray(R.array.job)) { viewModel.setJob(it) }
        sMajor.setAdapter(resources.getStringArray(R.array.major)) { viewModel.setMajor(it) }
    }

    private fun initSpinnerAdapter(list: Array<String>): ArrayAdapter<String> = ArrayAdapter(
        requireContext(),
        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        list
    )

    private fun Spinner.setAdapter(
        list: Array<String>,
        itemSelectListener: (item: String) -> Unit
    ) = apply {
        adapter = initSpinnerAdapter(list)
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                itemSelectListener(list[position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }
    }
}