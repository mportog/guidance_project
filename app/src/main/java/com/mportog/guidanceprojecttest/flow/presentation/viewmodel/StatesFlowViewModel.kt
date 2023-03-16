package com.mportog.guidanceprojecttest.flow.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import guidance_project.app.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

internal class StatesFlowViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _livedata = MutableLiveData(R.string.hello_world_default_label)
    val liveData: LiveData<Int> = _livedata

    private val _stateFlow = MutableStateFlow(R.string.hello_world_default_label)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableStateFlow(R.string.hello_world_default_label)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun onLiveDataButtonClick() {
        _livedata.value = R.string.live_data_btn_label
    }

    fun onStateFlowButtonClick() {
        _stateFlow.value = R.string.state_flow_btn_label
    }

    fun onSharedFlowButtonClick() {
        viewModelScope.launch {
            _sharedFlow.emit(R.string.shared_flow_btn_label)
        }
    }

    fun onFlowButtonClick(): Flow<String> {
        return flow {
            repeat(5) {
                emit("Flow $it")
                kotlinx.coroutines.delay(1000L)
            }
        }
    }

}