package com.elemental.templateapplication.di

import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.templateapplication.remote.network.services.ApiService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val serviceModule=Kodein.Module("serviceModule") {
    bind() from singleton { ApiService(instance(), instance()) }

    bind<ConnectivityInterceptor>() with singleton {
        ConnectivityInterceptorImpl(instance())
    }
}