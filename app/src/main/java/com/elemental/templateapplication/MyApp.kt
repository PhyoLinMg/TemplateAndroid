package com.elemental.templateapplication

import android.app.Application
import com.elemental.templateapplication.di.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class MyApp:Application(),KodeinAware {

    override val kodein: Kodein=Kodein.lazy {
        import(androidXModule(this@MyApp))
        import(serviceModule)
        import(dataSourceModule)
        import(repositoryModule)
        import(useCaseModule)
        import(viewModelModule)

    }

    override fun onCreate() {
        super.onCreate()
    }
}