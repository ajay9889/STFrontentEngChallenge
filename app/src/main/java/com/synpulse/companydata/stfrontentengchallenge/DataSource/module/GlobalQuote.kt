package com.synpulse.companydata.stfrontentengchallenge.DataSource.module

import com.google.gson.annotations.SerializedName
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote
import java.io.Serializable


data class GlobalQuote(
    @SerializedName("09. change")
    val change: String?,
    @SerializedName("10. change percent")
    val changePercent: String?,
    @SerializedName("03. high")
    val high: String?,
    @SerializedName("07. latest trading day")
    val latestTradingDay: String?,
    @SerializedName("04. low")
    val low: String?,
    @SerializedName("02. open")
    val `open`: String?,
    @SerializedName("08. previous close")
    val previousClose: String?,
    @SerializedName("05. price")
    val price: String?,
    @SerializedName("01. symbol")
    val symbol: String?,
    @SerializedName("06. volume")
    val volume: String?
): Serializable{
    companion object{
        fun GlobalQuote.toTbGlobalQoute()=TbGlobalQuote(
             change=this.change,
         changePercent=this.changePercent,
         high=this.high,
         latestTradingDay=this.latestTradingDay,
        low = this.low,
        open=this.open,
        previousClose=this.previousClose,
        price=this.price,
        symbol=this.symbol,
        volume=this.volume,
        )
    }
}