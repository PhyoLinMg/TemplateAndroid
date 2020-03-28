package com.elemental.templateapplication

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.GetService
import com.elemental.atantat.network.services.PostService
import com.elemental.atantat.network.services.UserLoginSignUpInterface
import com.elemental.templateapplication.repository.TestRepository
import com.elemental.templateapplication.repository.TestRepositoryImpl
import com.elemental.templateapplication.useCase.TestUseCase
import com.elemental.templateapplication.utils.BaseViewModelFactory
import com.elemental.templateapplication.utils.Constants
import com.elemental.templateapplication.utils.MySharedPreference
import com.elemental.templateapplication.utils.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindings.Singleton
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApp:Application(),KodeinAware {
    override val kodein: Kodein=Kodein.lazy {
        import(androidXModule(this@MyApp))

        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(instance()) }
        bind<String>() with singleton{
            MySharedPreference.getTokenFromPreference(instance())
        }
        bind() from singleton { GetService(instance(), instance()) }
        bind() from singleton { PostService(instance(),instance()) }
        bind() from singleton { UserLoginSignUpInterface(instance()) }

        bind<TestRepository>() with provider { TestRepositoryImpl(instance(),instance()) }


        bind() from singleton { TestUseCase(instance()) }


        bind<ViewModelProvider.Factory>() with singleton { BaseViewModelFactory(kodein.direct) }

        bindViewModel<SampleViewModel>() with provider {
            SampleViewModel(instance())
        }


        //singleton yay ya ml

//        bind() from singleton { GetService(instance(),instance()) }
//
//        bind() from singleton { PostService(instance(),instance()) }
//
//        bind() from singleton { UserLoginSignUpInterface(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
    }
}