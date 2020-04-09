package com.elemental.templateapplication.di

import com.elemental.templateapplication.repository.MajorImpl
import com.elemental.templateapplication.repository.TestRepository
import com.elemental.templateapplication.repository.TestRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val repositoryModule = Kodein.Module("repositoryModule") {
    bind<TestRepository>("periods") with provider { TestRepositoryImpl(instance()) }
    bind<TestRepository>("majors") with provider { MajorImpl(instance(),instance()) }
}