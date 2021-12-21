package com.synpulse.companydata.stfrontentengchallenge.DataSource.module
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class BestSearchMatchesData(
    @SerialName("bestMatches")
    val bestMatches: List<BestMatche>?
){
    companion object{
       fun getToDomainOnError() =BestSearchMatchesData(
        bestMatches= emptyList()
        )
    }
}