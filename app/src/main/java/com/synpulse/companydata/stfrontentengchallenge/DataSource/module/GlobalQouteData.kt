package com.synpulse.companydata.stfrontentengchallenge.DataSource.module

import com.google.gson.annotations.SerializedName

data class GlobalQouteData(
    @SerializedName("Global Quote")
    val globalQuote: GlobalQuote?
){
    companion object{
        fun getToDomainOnError() =GlobalQouteData(
            globalQuote= null
        )
    }
}