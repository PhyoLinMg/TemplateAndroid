package com.elemental.templateapplication.di

import com.elemental.templateapplication.useCase.TestUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val useCaseModule= Kodein.Module("useCaseModule"){
    bind() from singleton { TestUseCase(instance("periods")) }
}