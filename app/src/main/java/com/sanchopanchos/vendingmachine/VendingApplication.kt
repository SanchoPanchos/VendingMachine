package com.sanchopanchos.vendingmachine

import android.app.Application
import com.sanchopanchos.vendingmachine.di.repositoryModule
import com.sanchopanchos.vendingmachine.di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin

class VendingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(repositoryModule, viewModelModule)
        }
    }
}