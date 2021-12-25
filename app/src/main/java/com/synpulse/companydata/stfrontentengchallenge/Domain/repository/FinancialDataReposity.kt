package com.synpulse.companydata.stfrontentengchallenge.Domain.repository

import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.BestSearchMatchesData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import io.reactivex.Single

interface FinancialDataReposity {

    suspend fun getSearchEndpoint(keywords: String) : ViewState.Content<BestSearchMatchesData?>

     fun getQuoteEndpoint(symbol: String): Single<GlobalQouteData>

    suspend fun getDailyData(symbol: String) :ViewState.Content<TimeSerieseData>

    suspend fun getCompanyList() :List<CompanyListData>
}
