package com.peterstaranchuk.marvelheroesapp

import android.app.Application
import com.peterstaranchuk.marvelheroesapp.dependency_injection.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelHeroesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MarvelHeroesApplication)
            modules(networkModule)
        }
    }
}