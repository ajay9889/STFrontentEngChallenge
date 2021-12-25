package com.synpulse.companydata.stfrontentengchallenge
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import androidx.work.*
import com.synpulse.companydata.stfrontentengchallenge.KoinDepInject.appModule
import com.synpulse.companydata.stfrontentengchallenge.KoinDepInject.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class MainApplication : MultiDexApplication() {
    private val modules = listOf(
        appModule, viewModelModule
    )
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(modules)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}