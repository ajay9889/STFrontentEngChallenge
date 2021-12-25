package com.synpulse.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class TimeSerieseData(
    @SerializedName("Meta Data")
    val metaData: MetaData?=null,
    @SerializedName("Time Series (Daily)")
    val time_series_data: JsonElement?=null
){
    companion object{
        fun getToDomainOnError() =TimeSerieseData(
            metaData= null,
            time_series_data = null
        )
    }
}