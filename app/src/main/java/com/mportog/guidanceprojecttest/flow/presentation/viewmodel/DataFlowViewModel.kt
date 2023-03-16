package com.mportog.guidanceprojecttest.flow.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mportog.guidanceprojecttest.model.ui.DataFlow
import com.mportog.guidanceprojecttest.repository.FlowRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

internal class DataFlowViewModel(
    private val flowRepository: FlowRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _hasDelay = MutableLiveData(false)
    val hasDelay: LiveData<Boolean> = _hasDelay

    private val _dataFlow = MutableLiveData(DataFlow())
    val dataFlow: LiveData<DataFlow> = _dataFlow

    suspend fun getDataFlow() = flowRepository.getData(withDelay = hasDelay.value ?: false)
        .flowOn(ioDispatcher)
        .onStart { showLoading() }
        .onCompletion { hideLoading() }
        .onEach {
            // atualiza dados na tela
        }
        .catch {
            // mostra tela de erro
        }
        .launchIn(viewModelScope)

    private fun showLoading() {
        _isLoading.value = true
    }

    private fun hideLoading() {
        _isLoading.value = false
    }

}