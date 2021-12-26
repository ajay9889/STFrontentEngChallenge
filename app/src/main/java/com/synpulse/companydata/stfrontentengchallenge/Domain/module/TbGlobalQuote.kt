package com.synpulse.companydata.stfrontentengchallenge.Domain.module

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "TbGlobalQuote", primaryKeys = ["symbol"] )
data class TbGlobalQuote(
    val change: String?,
    val changePercent: String?,
    val high: String?,
    val latestTradingDay: String?,
    val low: String?,
    val open: String?,
    val previousClose: String?,
    val price: String?,
    val symbol: String?,
    val volume: String?
): Serializable {

}