package com.elemental.templateapplication.remote.network.services


import android.content.Context
import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.templateapplication.data.model.Majors
import com.elemental.templateapplication.data.model.Periods
import com.elemental.templateapplication.utils.Constants
import com.elemental.templateapplication.utils.MySharedPreference
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService{

    // Need to Write Singleton Class
    @GET("periods")
    fun getPeriods(): Deferred<Response<Periods>>

    @GET("majors")
    fun getMajors():Deferred<Response<Majors>>

    companion object {
       operator fun invoke(
           connectivityInterceptor: ConnectivityInterceptor,context:Context
       ) : ApiService {


           val requestInterceptor = Interceptor { chain ->
               val token=MySharedPreference.getTokenFromPreference(context)
               val request = chain.request()
                   .newBuilder()
                       if(token!=null)
                           request.addHeader("Authorization","Bearer $token")

               return@Interceptor chain.proceed(request.build())
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
               .create(ApiService::class.java)
       }
    }
}