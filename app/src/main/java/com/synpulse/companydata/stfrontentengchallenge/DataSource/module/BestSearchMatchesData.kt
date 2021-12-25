package com.synpulse.companydata.stfrontentengchallenge.DataSource.module
import com.google.gson.annotations.SerializedName

data class BestSearchMatchesData(
    @SerializedName("bestMatches")
    val bestMatches: List<BestMatche?>?
){
    companion object{
       fun getToDomainOnError() =BestSearchMatchesData(
        bestMatches= emptyList()
        )
    }
}