package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.CreateFsResponseUseCase
import com.surveasy.surveasy.presentation.util.ErrorMsg.SURVEY_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FsViewModel @Inject constructor(
    private val createFsResponseUseCase: CreateFsResponseUseCase
) : ViewModel() {
    private val job = MutableStateFlow("")
    private val major = MutableStateFlow("")
    val english = MutableStateFlow(true)
    private val city = MutableStateFlow("")
    private val family = MutableStateFlow("")
    private val pet = MutableStateFlow(-1)

    private val _uiState = MutableStateFlow(FsValidState())
    val uiState: StateFlow<FsValidState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<FsEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<FsEvents> = _events.asSharedFlow()

    init {
        observeJob()
        observeMajor()
        observeCity()
        observeFamily()
        observePet()
    }

    fun createFsResponse() {
        createFsResponseUseCase(
            english.value,
            city.value,
            family.value,
            job.value,
            major.value.ifEmpty { null },
            pet.value
        ).onEach { state ->
            when (state) {
                is BaseState.Success -> _events.emit(FsEvents.NavigateToDone)
                else -> _events.emit(FsEvents.ShowSnackBar(SURVEY_ERROR))
            }
        }.launchIn(viewModelScope)
    }

    fun navigateToNext(type: FsNavType) {
        viewModelScope.launch {
            when (type) {
                FsNavType.TO_DONE -> _events.emit(FsEvents.NavigateToDone)
                FsNavType.TO_BACK -> _events.emit(FsEvents.NavigateToBack)
            }
        }
    }

    fun setJob(select: String) {
        job.value = select
    }

    private fun observeJob() {
        job.onEach { job ->
            _uiState.update {
                it.copy(jobValid = job != JOB_DEFAULT, isStudent = job == JOB_STUDENT)
            }
        }.launchIn(viewModelScope)
    }


    fun setMajor(select: String) {
        major.value = select
    }

    private fun observeMajor() {
        major.onEach { major ->
            _uiState.update {
                it.copy(majorValid = major != MAJOR_DEFAULT)
            }
        }.launchIn(viewModelScope)
    }

    fun setEnglish(participate: Boolean) {
        viewModelScope.launch { english.emit(participate) }
    }

    fun setCity(select: String) {
        city.value = select
    }

    private fun observeCity() {
        city.onEach { city ->
            _uiState.update {
                it.copy(cityValid = city != CITY_DEFAULT)
            }
        }.launchIn(viewModelScope)
    }

    fun setFamily(select: String) {
        family.value = select
    }

    private fun observeFamily() {
        family.onEach { family ->
            _uiState.update {
                it.copy(familyValid = family != FAMILY_DEFAULT)
            }
        }.launchIn(viewModelScope)
    }

    fun setPet(state: Int) {
        pet.value = state
    }

    private fun observePet() {
        pet.onEach { pet ->
            _uiState.update {
                it.copy(petValid = pet != RADIO_DEFAULT)
            }
        }.launchIn(viewModelScope)
    }

    fun navigateToList() {
        viewModelScope.launch { _events.emit(FsEvents.NavigateToList) }
    }

    companion object {
        const val JOB_DEFAULT = "직업을 선택해주세요"
        const val MAJOR_DEFAULT = "소속 계열을 선택해주세요"
        const val JOB_STUDENT = "대학생"
        const val CITY_DEFAULT = "시/도"
        const val FAMILY_DEFAULT = "가구 형태를 선택해주세요"
        const val RADIO_DEFAULT = -1
    }
}

sealed class FsEvents {
    data object NavigateToDone : FsEvents()
    data object NavigateToBack : FsEvents()
    data object NavigateToList : FsEvents()
    data class ShowToastMsg(val msg: String) : FsEvents()
    data class ShowSnackBar(val msg: String) : FsEvents()
}


data class FsValidState(
    val jobValid: Boolean = false,
    val isStudent: Boolean = false,
    val majorValid: Boolean = false,
    val cityValid: Boolean = false,
    val petValid: Boolean = false,
    val familyValid: Boolean = false,
)


enum class FsNavType {
    TO_DONE,
    TO_BACK,
}