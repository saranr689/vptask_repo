package com.vp.task.network

sealed class BaseNetworkCallResult<T>(val data : T?= null, val message : String? = null)
{
    class Sucess<T>(data: T?):BaseNetworkCallResult<T>(data)
    class Error<T>(message: String?, data: T?= null):BaseNetworkCallResult<T>(data,message)

}
