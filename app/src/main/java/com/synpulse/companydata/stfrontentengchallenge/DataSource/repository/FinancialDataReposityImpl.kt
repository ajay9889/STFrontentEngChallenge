package com.synpulse.companydata.stfrontentengchallenge.DataSource.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.synpulse.companydata.ApiService.ApiEndpoints
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.BestSearchMatchesData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.Domain.repository.FinancialDataReposity
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import retrofit2.Retrofit
import com.opencsv.CSVReader
import com.synpulse.companydata.stfrontentengchallenge.R
import java.io.File
import java.io.FileReader


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

    override suspend fun getCompanyList(symbol: String): List<CompanyListData> {
        val reader = CSVReader(FileReader("file:///android_res/raw/listing_status.csv"))
        var newItemd= arrayListOf<CompanyListData>()
        reader.readAll()?.map {
            Log.d("DATA" , "$it")
            newItemd.add(
                CompanyListData(
                    symbol = it[0],
                    name = it[0],
                    gainloss = "+10",
                    iconImage="",
                    isFollwoing="0"
                )
            )
        }
       return newItemd
    }
}