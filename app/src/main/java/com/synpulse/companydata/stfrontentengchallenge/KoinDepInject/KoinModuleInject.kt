package com.synpulse.companydata.stfrontentengchallenge.KoinDepInject

import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.Core.networkutils.CacheInterceptor
import com.synpulse.companydata.Core.networkutils.OnlineCacheInterceptor
import com.synpulse.companydata.stfrontentengchallenge.BuildConfig
import com.synpulse.companydata.stfrontentengchallenge.DataSource.repository.FinancialDataReposityImpl
import com.synpulse.companydata.stfrontentengchallenge.MainApplication
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard.HomeViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        Databasehelper.getDatabase(androidContext())
    }

    single{
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
            .addInterceptor(CacheInterceptor(androidContext()))
            .addNetworkInterceptor(OnlineCacheInterceptor())
            .cache(Cache(File(androidContext().cacheDir, "api"), 1024 * 1024 * 1024L)).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_DOMAIN)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
    single {
        FinancialDataReposityImpl(androidContext() , get())
    }
}
val viewModelModule = module {
    viewModel {
        UserSignInViewModel(androidApplication() as MainApplication)
    }
    viewModel {
        HomeViewModel(androidApplication() as MainApplication)
    }
}
