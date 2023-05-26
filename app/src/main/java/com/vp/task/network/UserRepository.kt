package com.vp.task.network

import com.vp.task.model.PostComments
import com.vp.task.model.UsersList
import com.vp.task.model.UsersPosts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiServiceCall: UserApiServiceCall) :
    BaseApiResponse() {

    suspend fun getUserList(): Flow<BaseNetworkCallResult<UsersList>> {
        return flow<BaseNetworkCallResult<UsersList>> { emit(safeApiCall { userApiServiceCall.getUserList() }) }
    }

    suspend fun getUserPosts(userId: String): Flow<BaseNetworkCallResult<UsersPosts>> {
        return flow<BaseNetworkCallResult<UsersPosts>> {
            emit(safeApiCall {
                userApiServiceCall.getUserPosts(
                    userId
                )
            })
        }
    }

    suspend fun getPostComments(postId: String): Flow<BaseNetworkCallResult<PostComments>> {
        return flow<BaseNetworkCallResult<PostComments>> {
            emit(safeApiCall {
                userApiServiceCall.getPostComments(
                    postId
                )
            })
        }
    }
}