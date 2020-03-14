package com.elemental.atantat.network.services


import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.templateapplication.utils.Constants

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PublicService {





    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): PublicService {


            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()


            return Retrofit.Builder()
                .baseUrl(Constants.ONLINE_API_END)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(PublicService::class.java)
        }
    }
}