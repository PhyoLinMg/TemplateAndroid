package com.elemental.atantat.network.services


import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.templateapplication.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


interface GetService{


    companion object {
       operator fun invoke(
           connectivityInterceptor: ConnectivityInterceptor, token:String
       ) : GetService {


           val requestInterceptor = Interceptor { chain ->
               val request = chain.request()
                   .newBuilder()
                   .addHeader("Authorization","Bearer "+token)
                   .build()

               return@Interceptor chain.proceed(request)
           }

           val okHttpClient = OkHttpClient.Builder()
              .addInterceptor(requestInterceptor)
               .addInterceptor(connectivityInterceptor)
               .build()

           return Retrofit.Builder()
               .baseUrl(Constants.ONLINE_API_END)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(CoroutineCallAdapterFactory())
               .client(okHttpClient)
               .build()
               .create(GetService::class.java)
       }
    }
}