package com.example.tdd.viewmodel.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tdd.BuildConfig
import com.example.tdd.model.area.Area
import com.example.tdd.model.area.AreaRepository
import com.example.tdd.model.common.ErrorHandler
import com.example.tdd.ui.common.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AreaViewModel(
    private val repo: AreaRepository,
    private val errorHandler: ErrorHandler
): ViewModel() {

    private var _state = MutableStateFlow<ResourceState<List<Area>>>(ResourceState.Loading)
    val state: StateFlow<ResourceState<List<Area>>> = _state

    fun refresh(){
        viewModelScope.launch {
            _state.value = ResourceState.Loading
            try {
                val data = repo.getAreaCode("AND", "APP", BuildConfig.SERVICE_KEY)
                _state.value = ResourceState.Success(data)
            } catch (t: Throwable) {
                _state.value = ResourceState.Error(errorHandler.wrap(t).message ?: "Unknown Error")
            }
        }
    }
}