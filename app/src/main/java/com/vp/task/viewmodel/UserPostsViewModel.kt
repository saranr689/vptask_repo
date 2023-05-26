package com.vp.task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp.task.model.PostComments
import com.vp.task.model.UsersPosts
import com.vp.task.network.BaseNetworkCallResult
import com.vp.task.network.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPostsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userPostResponse: MutableLiveData<BaseNetworkCallResult<UsersPosts>> =
        MutableLiveData()
    val userPostResponse: LiveData<BaseNetworkCallResult<UsersPosts>> = _userPostResponse

    private val _userPostCommentsResponse: MutableLiveData<BaseNetworkCallResult<PostComments>> =
        MutableLiveData()
    val userPostCommentsResponse: LiveData<BaseNetworkCallResult<PostComments>> =
        _userPostCommentsResponse

    fun getUserPosts(userId: String) = viewModelScope.launch {
        userRepository.getUserPosts(userId).collect() { values ->
            _userPostResponse.value = values
        }
    }

    fun getPostComments(postId: String) = viewModelScope.launch {
        userRepository.getPostComments(postId).collect() { values ->
            _userPostCommentsResponse.value = values
        }
    }
}