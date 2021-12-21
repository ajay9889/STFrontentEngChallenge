package com.synpulse.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class TimeSerieseData(
    @SerialName("Meta Data")
    val metaData: MetaData?=null,
    @SerialName("Time Series (Daily)")
    val time_series_data: JsonElement?=null
){
    companion object{
        fun getToDomainOnError() =TimeSerieseData(
            metaData= null,
            time_series_data = null
        )
    }
}