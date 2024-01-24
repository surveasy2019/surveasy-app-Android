package com.surveasy.surveasy.presentation.main.survey.fs

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentFs2Binding
import com.surveasy.surveasy.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class Fs2Fragment : BaseFragment<FragmentFs2Binding>(R.layout.fragment_fs2) {
    private val viewModel: FsViewModel by activityViewModels()
    override fun initData() {

    }

    override fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is FsEvents.NavigateToDone -> findNavController().toDone()
                    is FsEvents.NavigateToBack -> findNavController().navigateUp()
                    else -> Unit
                }
            }
        }
        repeatOnStarted {
            viewModel.uiState.collectLatest {
                binding.btnSubmit.isEnabled =
                    it.cityValid && it.marryValid && it.petValid && it.familyValid && it.housingValid
            }
        }
    }

    override fun initView() {
        initSpinner()
        marryRadioListener()
        petRadioListener()
        binding.btnSubmit.setOnClickListener { viewModel.test() }
    }

    private fun NavController.toDone() {
        navigate(Fs2FragmentDirections.actionFs2FragmentToFsDoneFragment())
    }

    private fun marryRadioListener() {
        binding.rgMarry.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_marry_no -> viewModel.setMarry(0)
                R.id.rb_marry_no -> viewModel.setMarry(1)
                R.id.rb_marry_etc -> viewModel.setMarry(2)
            }
        }
    }

    private fun petRadioListener() {
        binding.rgPet.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_pet_no -> viewModel.setPet(0)
                R.id.rb_pet_cat -> viewModel.setPet(1)
                R.id.rb_pet_dog -> viewModel.setPet(2)
                R.id.rb_pet_etc -> viewModel.setPet(3)
            }
        }
    }

    private fun initSpinner() = with(binding) {
        sLocation.setAdapter(resources.getStringArray(R.array.city)) { viewModel.setCity(it) }
        sHouse.setAdapter(resources.getStringArray(R.array.housingType)) { viewModel.setHousing(it) }
        sFamily.setAdapter(resources.getStringArray(R.array.familyType)) { viewModel.setFamily(it) }
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