package com.synpulse.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.annotations.SerializedName
import com.synpulse.companydata.stfrontentengchallenge.Core.Util.Utils


data class BestMatche(
    @SerializedName("8. currency")
    val currency: String?,
    @SerializedName("6. marketClose")
    val marketClose: String?,
    @SerializedName("5. marketOpen")
    val marketOpen: String?,
    @SerializedName("9. matchScore")
    val matchScore: String?,
    @SerializedName("2. name")
    val name: String?,
    @SerializedName("4. region")
    val region: String?,
    @SerializedName("1. symbol")
    val symbol: String?,
    @SerializedName("7. timezone")
    val timezone: String?,
    @SerializedName("3. type")
    val type: String?
) {
    companion object{
       public fun BestMatche.getToCompanyDomain() = let {
           CompanyListData(
               symbol = it.symbol!!,
               name   =  it.name!!,
               gainloss= it.type!!,
               iconImage= "",
               isFollwoing= "",
               trade_date = Utils.getCurrentDate()
           )
       }
    }
}