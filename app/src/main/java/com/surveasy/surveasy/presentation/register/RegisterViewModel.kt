package com.surveasy.surveasy.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private var _agreeAll = MutableLiveData<Boolean>()
    val agreeAll get() = _agreeAll
    private var _agreeMust1 = MutableLiveData<Boolean>()
    val agreeMust1 get() = _agreeMust1
    private var _agreeMust2 = MutableLiveData<Boolean>()
    val agreeMust2 get() = _agreeMust2
    private var _agreeMarketing = MutableLiveData<Boolean>()
    val agreeMarketing get() = _agreeMarketing

    init {
        _agreeAll.value = false
        _agreeMust1.value = false
        _agreeMust2.value = false
        _agreeMarketing.value = false

    }


    fun checkAgree(index : Int) {
        when(index){
            0 -> {

                if(agreeAll.value == false){
                    _agreeAll.value = true
                    _agreeMust1.value = true
                    _agreeMust2.value = true
                    _agreeMarketing.value = true
                } else {
                    _agreeAll.value = false
                    _agreeMust1.value = false
                    _agreeMust2.value = false
                    _agreeMarketing.value = false
                }


//                _agreeMust1.value = agreeAll.value == false
//                _agreeMust2.value = agreeAll.value == false
//                _agreeMarketing.value = agreeAll.value == false
            }
            1 -> {
                _agreeMust1.value = if(_agreeMust1.value == true) false else true
                _agreeAll.value = checkAllChecked()
            }
        }
    }

    private fun checkAllChecked() = (agreeMust1.value == true) && (agreeMust2.value == true) && (agreeMarketing.value == true)
}