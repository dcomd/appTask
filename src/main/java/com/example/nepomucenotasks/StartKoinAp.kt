package com.example.nepomucenotasks

import android.app.Application
import com.example.nepomucenotasks.di.listModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import com.facebook.stetho.Stetho
import okhttp3.OkHttpClient
import com.facebook.stetho.okhttp3.StethoInterceptor

class StartKoinAp : Application() {

    var initializerBuilder: Stetho.InitializerBuilder? = null

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@StartKoinAp)
            modules(listModules)
        }

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        )

    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}