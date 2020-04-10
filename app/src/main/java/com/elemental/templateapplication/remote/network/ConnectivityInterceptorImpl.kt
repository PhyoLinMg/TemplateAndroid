package com.elemental.atantat.network

import android.content.Context
import android.net.ConnectivityManager
import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.templateapplication.utils.MyNetworkUty.isConnectionOn
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context: Context): ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnectionOn(appContext))
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

}