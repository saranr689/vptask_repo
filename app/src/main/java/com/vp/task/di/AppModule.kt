package com.vp.task.di

import com.vp.task.network.UserApiService
import com.vp.task.utils.Utlis
import com.vp.task.ui.adapter.PostListAdapter
import com.vp.task.ui.adapter.PostsCommentListAdapter
import com.vp.task.ui.adapter.UsersListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideGsonConvertFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Utlis.BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    fun provideUsersListAdapter(): UsersListAdapter = UsersListAdapter()

    @Provides
    fun providePostListAdapter(): PostListAdapter = PostListAdapter()

    @Provides
    fun providePostComentListAdapter(): PostsCommentListAdapter = PostsCommentListAdapter()
}