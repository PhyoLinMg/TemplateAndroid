package com.elemental.templateapplication.di

import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.GetService
import com.elemental.atantat.network.services.PostService
import com.elemental.atantat.network.services.UserLoginSignUpInterface
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val serviceModule=Kodein.Module("serviceModule") {
    bind() from singleton { GetService(instance(), instance()) }
    bind() from singleton { PostService(instance(), instance()) }
    bind() from singleton { UserLoginSignUpInterface(instance()) }
    bind<ConnectivityInterceptor>() with singleton {
        ConnectivityInterceptorImpl(instance())
    }
}