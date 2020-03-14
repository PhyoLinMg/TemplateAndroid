package com.elemental.atantat.network.services


import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.templateapplication.utils.Constants

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface PostService {





    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): PostService {


            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()


            return Retrofit.Builder()
                .baseUrl(Constants.ONLINE_API_END)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(PostService::class.java)
        }
    }
}