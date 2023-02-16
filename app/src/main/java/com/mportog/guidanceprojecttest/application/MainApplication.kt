package com.mportog.guidanceprojecttest.application

import android.app.Application
import com.mportog.guidanceprojecttest.di.flowModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(flowModule)
        }

    }
}

