package com.vp.task.network

import javax.inject.Inject

class UserApiServiceCall @Inject constructor(val userApiService: UserApiService) {

    suspend fun getUserList() = userApiService.getUserList()
}