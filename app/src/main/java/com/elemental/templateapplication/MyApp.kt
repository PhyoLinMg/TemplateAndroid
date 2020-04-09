package com.elemental.templateapplication

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.elemental.templateapplication.di.repositoryModule
import com.elemental.templateapplication.di.serviceModule
import com.elemental.templateapplication.di.useCaseModule
import com.elemental.templateapplication.di.viewModelModule
import com.elemental.templateapplication.repository.MajorImpl
import com.elemental.templateapplication.repository.TestRepository
import com.elemental.templateapplication.repository.TestRepositoryImpl
import com.elemental.templateapplication.useCase.TestUseCase
import com.elemental.templateapplication.utils.BaseViewModelFactory
import com.elemental.templateapplication.utils.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.direct
import org.kodein.di.generic.*

class MyApp:Application(),KodeinAware {

    override val kodein: Kodein=Kodein.lazy {
        import(androidXModule(this@MyApp))
        import(serviceModule)
        import(repositoryModule)
        import(useCaseModule)
        import(viewModelModule)

    }

    override fun onCreate() {
        super.onCreate()
    }
}