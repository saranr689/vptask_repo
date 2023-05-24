package com.vp.task.network

import com.vp.task.model.UserDetails
import com.vp.task.model.UsersList
import com.vp.task.model.UsersPosts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("/users")
    suspend fun getUserList(): Response<UsersList>

    @GET("/users/{userid}")
    suspend fun getUserDetails(@Path("userid") id: String): Response<UserDetails>

    @GET("users/{userid}/posts")
    fun getUserPosts(@Path("userid") id: String): Response<UsersPosts>

    @GET("/posts/{postsId}/comments")
    fun getPostsComment(@Path("postsId") postsId: String): Response<UsersPosts>
}