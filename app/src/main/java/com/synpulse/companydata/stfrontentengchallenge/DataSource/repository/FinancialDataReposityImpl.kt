package com.synpulse.companydata.stfrontentengchallenge.DataSource.repository

import android.content.Context
import com.synpulse.companydata.ApiService.ApiEndpoints
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.BestSearchMatchesData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.Domain.repository.FinancialDataReposity
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import retrofit2.Retrofit

class FinancialDataReposityImpl(val context: Context, val retrofit: Retrofit): FinancialDataReposity {
    override suspend fun getSearchEndpoint(keywords: String): ViewState.Content<BestSearchMatchesData> {
        retrofit.create(ApiEndpoints::class.java).searchFinAPi(keywords).let {
            if (it.isSuccessful) {
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }
        }
        return ViewState.Content(BestSearchMatchesData.getToDomainOnError())
    }

    override suspend fun getQuoteEndpoint(symbol: String): ViewState.Content<GlobalQouteData> {
            retrofit.create(ApiEndpoints::class.java).historicalFinData(symbol).let {
                if (it.isSuccessful) {
                    it.body()?.let {
                        return ViewState.Content(it)
                    }
                }
            }
            return ViewState.Content(GlobalQouteData.getToDomainOnError())
        }

    override suspend fun getDailyData(symbol: String): ViewState.Content<TimeSerieseData> {
            retrofit.create(ApiEndpoints::class.java).dailyRealtimeFinData(symbol).let {
                if (it.isSuccessful) {
                    it.body()?.let {
                        return ViewState.Content(it)
                    }
                }
            }
            return ViewState.Content(TimeSerieseData.getToDomainOnError())
    }
}