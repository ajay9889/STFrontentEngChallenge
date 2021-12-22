package com.synpulse.companydata.stfrontentengchallenge.Domain.repository

import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.BestSearchMatchesData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState

interface FinancialDataReposity {

    suspend fun getSearchEndpoint(keywords: String) : ViewState.Content<BestSearchMatchesData>

    suspend fun getQuoteEndpoint(symbol: String):ViewState.Content<GlobalQouteData>

    suspend fun getDailyData(symbol: String) :ViewState.Content<TimeSerieseData>

    suspend fun getCompanyList(symbol: String) :List<CompanyListData>
}
