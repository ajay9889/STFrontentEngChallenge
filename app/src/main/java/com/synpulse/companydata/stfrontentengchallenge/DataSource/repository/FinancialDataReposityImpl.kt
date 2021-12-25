package com.synpulse.companydata.stfrontentengchallenge.DataSource.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
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


import android.content.res.AssetManager
import io.reactivex.Single
import java.io.*
import java.lang.RuntimeException


class FinancialDataReposityImpl(val context: Context, val retrofit: Retrofit): FinancialDataReposity {
    override suspend fun getSearchEndpoint(keywords: String): ViewState.Content<BestSearchMatchesData?> {
        retrofit.create(ApiEndpoints::class.java).searchFinAPi(keywords).let {
            if (it.isSuccessful) {
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }
        }
        return ViewState.Content(BestSearchMatchesData.getToDomainOnError())
    }

    override fun getQuoteEndpoint(symbol: String): Single<GlobalQouteData> {
        return retrofit.create(ApiEndpoints::class.java).historicalFinData(symbol)
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


    override suspend fun getCompanyList(): List<CompanyListData> {
        var resultList= arrayListOf<CompanyListData>()
        val inputStream: InputStream? = context.getResources().openRawResource(R.raw.listing_status)

        if(inputStream==null) return resultList

        try {
            val reader = BufferedReader(InputStreamReader(inputStream))
            var csvLine: String?=""
            while (reader.readLine().also { csvLine = it } != null) {
                csvLine?.split(",".toRegex())?.toTypedArray()?.let { row->
                    row.get(0).trim().let{
                        if(it.length>0){
                            resultList.add(CompanyListData(
                                symbol = it,
                                name = it,
                                gainloss = "+10",
                                iconImage="",
                                isFollwoing="0"
                            ))
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            throw RuntimeException("Error in reading CSV file: $ex")
        } finally {
            inputStream.close()
        }
       return resultList
    }
}