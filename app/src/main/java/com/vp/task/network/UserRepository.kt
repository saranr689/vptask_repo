package com.vp.task.network

import com.vp.task.model.UsersList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiServiceCall: UserApiServiceCall) :
    BaseApiResponse() {

    suspend fun getUserList(): Flow<BaseNetworkCallResult<UsersList>> {
        return flow<BaseNetworkCallResult<UsersList>>{emit(safeApiCall { userApiServiceCall.getUserList() })}
    }
}