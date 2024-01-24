package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FsViewModel @Inject constructor() : ViewModel() {
    private val job = MutableStateFlow("")
    private val major = MutableStateFlow("")
    val english = MutableStateFlow(true)
    private val military = MutableStateFlow(-1)

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
                it.copy(militaryValid = military != -1)
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        const val JOB_DEFAULT = "직업을 선택해주세요"
        const val MAJOR_DEFAULT = "소속 계열을 선택해주세요"
        const val JOB_STUDENT = "대학생"
        const val CITY_DEFAULT = "시/도"
        const val REGION_DEFAULT = "시/군/구"
    }
}

sealed class FsEvents {
    data object NavigateToInput2 : FsEvents()
    data object NavigateToDone : FsEvents()
    data object NavigateToBack : FsEvents()
}


data class FsValidState(
    val jobValid: Boolean = false,
    val isStudent: Boolean = false,
    val majorValid: Boolean = false,
    val militaryValid: Boolean = false,
    val cityValid: Boolean = false,
)


enum class FsNavType {
    TO_INPUT2,
    TO_DONE,
    TO_BACK,
}