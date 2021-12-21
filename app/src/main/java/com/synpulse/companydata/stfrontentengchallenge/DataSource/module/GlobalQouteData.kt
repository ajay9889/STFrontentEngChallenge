package com.synpulse.companydata.stfrontentengchallenge.DataSource.module


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalQouteData(
    @SerialName("Global Quote")
    val globalQuote: GlobalQuote?
){
    companion object{
        fun getToDomainOnError() =GlobalQouteData(
            globalQuote= null
        )
    }
}