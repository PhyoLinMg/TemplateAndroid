package com.elemental.templateapplication.di

import com.elemental.templateapplication.data.dataSourceContract.RemoteDataSource
import com.elemental.templateapplication.remote.source.RemoteDataSourceImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val dataSourceModule= Kodein.Module("dataSourceModule"){
    bind<RemoteDataSource>() with singleton { RemoteDataSourceImpl(instance()) }
}