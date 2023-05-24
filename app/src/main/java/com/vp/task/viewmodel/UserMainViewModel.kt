package com.vp.task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp.task.model.UsersList
import com.vp.task.network.BaseNetworkCallResult
import com.vp.task.network.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserMainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _response: MutableLiveData<BaseNetworkCallResult<UsersList>> = MutableLiveData()
    val response: LiveData<BaseNetworkCallResult<UsersList>> = _response
    fun getUserListResponse() = viewModelScope.launch {
        userRepository.getUserList().collect() { values -> _response.value = values }
    }
}