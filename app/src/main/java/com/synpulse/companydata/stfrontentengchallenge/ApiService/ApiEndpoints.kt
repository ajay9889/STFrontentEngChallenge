package com.synpulse.companydata.ApiService
import com.synpulse.companydata.stfrontentengchallenge.BuildConfig
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.BestSearchMatchesData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoints {

    // Search Company
    @GET(BuildConfig.API_KEY+"&function=SYMBOL_SEARCH")
    suspend fun searchFinAPi(@Query("keywords") keywords: String?): Response<BestSearchMatchesData>

    @GET(BuildConfig.API_KEY+"&function=GLOBAL_QUOTE")
    suspend fun historicalFinData(@Query("symbol") symbol: String?): Response<GlobalQouteData>


    @GET(BuildConfig.API_KEY+"&function=TIME_SERIES_DAILY")
    suspend fun dailyRealtimeFinData(@Query("symbol") symbol: String?): Response<TimeSerieseData>
}