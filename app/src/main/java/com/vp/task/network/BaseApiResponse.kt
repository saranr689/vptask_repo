package com.vp.task.network

import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): BaseNetworkCallResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { return BaseNetworkCallResult.Sucess(response.body()) }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): BaseNetworkCallResult<T> =
        BaseNetworkCallResult.Error("Network call failed $errorMessage")}