package com.elemental.templateapplication.di

import androidx.lifecycle.ViewModelProvider
import com.elemental.templateapplication.presentation.viewmodels.SampleViewModel
import com.elemental.templateapplication.utils.BaseViewModelFactory
import com.elemental.templateapplication.utils.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val viewModelModule= Kodein.Module("viewModelModule"){
    bind<ViewModelProvider.Factory>() with singleton { BaseViewModelFactory(kodein.direct) }

    bindViewModel<SampleViewModel>() with provider {
        SampleViewModel(
            instance()
        )
    }
}