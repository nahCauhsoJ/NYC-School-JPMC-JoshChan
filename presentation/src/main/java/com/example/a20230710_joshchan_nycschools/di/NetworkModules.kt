package com.example.a20230710_joshchan_nycschools.di

import com.example.a20230710_joshchan_nycschools.ConstData
import com.example.a20230710_joshchan_nycschools.api.ApiNycData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {
    @Provides
    @Singleton
    fun providesApiNews(): ApiNycData = Retrofit.Builder()
        .baseUrl(ConstData.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client( OkHttpClient().newBuilder()
            .build()
        )
        .build()
        .create()

    @Provides
    @Singleton
    fun providesIODispatcher() = Dispatchers.IO
}