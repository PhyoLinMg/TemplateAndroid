package com.elemental.templateapplication

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.elemental.templateapplication.utils.BaseViewModelFactory
import com.elemental.templateapplication.utils.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApp:Application(),KodeinAware {
    override val kodein: Kodein=Kodein.lazy {
        import(androidXModule(this@MyApp))
        bind<ViewModelProvider.Factory>() with singleton { BaseViewModelFactory(kodein.direct) }

        bindViewModel<SampleViewModel>() with provider {
            SampleViewModel(instance())
        }


    }
}