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
    private val military = MutableStateFlow(-1)
    private val city = MutableStateFlow("")
    private val housing = MutableStateFlow("")
    private val family = MutableStateFlow("")
    private val marry = MutableStateFlow(-1)
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
        observeMilitary()
        observeHousing()
        observeCity()
        observeFamily()
        observeMarry()
        observePet()
    }

    fun createFsResponse() {
        createFsResponseUseCase(
            english.value,
            "SEOUL",
            "서대문구",
            family.value,
            housing.value,
            "UNDERGRADUATE",
            "대학",
            "SOCIAL",
            marry.value,
            military.value,
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
                FsNavType.TO_INPUT2 -> _events.emit(FsEvents.NavigateToInput2)
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

    fun setMilitary(state: Int) {
        military.value = state
    }

    private fun observeMilitary() {
        military.onEach { military ->
            _uiState.update {
                it.copy(militaryValid = military != RADIO_DEFAULT)
            }
        }.launchIn(viewModelScope)
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

    fun setHousing(select: String) {
        housing.value = select
    }

    private fun observeHousing() {
        housing.onEach { housing ->
            _uiState.update {
                it.copy(housingValid = housing != HOUSING_DEFAULT)
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

    fun setMarry(state: Int) {
        marry.value = state
    }

    private fun observeMarry() {
        marry.onEach { marry ->
            _uiState.update {
                it.copy(marryValid = marry != RADIO_DEFAULT)
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

    companion object {
        const val JOB_DEFAULT = "직업을 선택해주세요"
        const val MAJOR_DEFAULT = "소속 계열을 선택해주세요"
        const val JOB_STUDENT = "대학생"
        const val CITY_DEFAULT = "시/도"
        const val REGION_DEFAULT = "시/군/구"
        const val HOUSING_DEFAULT = "주거 형태를 선택해주세요"
        const val FAMILY_DEFAULT = "가구 형태를 선택해주세요"
        const val RADIO_DEFAULT = -1
    }
}

sealed class FsEvents {
    data object NavigateToInput2 : FsEvents()
    data object NavigateToDone : FsEvents()
    data object NavigateToBack : FsEvents()
    data class ShowToastMsg(val msg: String) : FsEvents()
    data class ShowSnackBar(val msg: String) : FsEvents()
}


data class FsValidState(
    val jobValid: Boolean = false,
    val isStudent: Boolean = false,
    val majorValid: Boolean = false,
    val militaryValid: Boolean = false,
    val cityValid: Boolean = false,
    val marryValid: Boolean = false,
    val petValid: Boolean = false,
    val housingValid: Boolean = false,
    val familyValid: Boolean = false,
)


enum class FsNavType {
    TO_INPUT2,
    TO_DONE,
    TO_BACK,
}