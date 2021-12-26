package com.synpulse.companydata.stfrontentengchallenge.Domain.module

import androidx.room.Entity
import com.synpulse.companydata.stfrontentengchallenge.Core.Util.Utils
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import java.io.Serializable

@Entity(tableName = "TbGlobalQuote", primaryKeys = ["symbol"] )
data class TbGlobalQuote(
    val change: String,
    val changePercent: String,
    val high: String,
    val latestTradingDay: String,
    val low: String,
    val open: String,
    val previousClose: String,
    val price: String,
    val symbol: String,
    val volume: String
): Serializable {
    companion object{

        fun TbGlobalQuote.toGlobalQoutes()= CompanyListData(
            symbol = this.symbol,
            name =this.symbol,
            gainloss = this.low,
            iconImage = this.change,
            trade_date = this.latestTradingDay,
            isFollwoing = "1"
        )
    }
}