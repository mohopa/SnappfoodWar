package ir.mhp.snappfoddwar

import android.app.Application
import ir.mhp.snappfoddwar.di.presentationModule
import ir.mhp.snappfoddwar.di.serviceModule
import ir.mhp.snappfoddwar.di.appModule
import ir.mhp.snappfoddwar.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(allModules())
        }
    }

    private fun allModules(): List<Module> {
        return listOf(
            appModule,
            networkModule,
            ir.example.data.di.repositoryModule,
                serviceModule,
            ir.mhp.domain.di.useCaseModule,
                presentationModule
        )
    }

}