package com.synpulse.companydata.stfrontentengchallenge.DataSource.module


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BestMatche(
    @SerialName("8. currency")
    val currency: String?,
    @SerialName("6. marketClose")
    val marketClose: String?,
    @SerialName("5. marketOpen")
    val marketOpen: String?,
    @SerialName("9. matchScore")
    val matchScore: String?,
    @SerialName("2. name")
    val name: String?,
    @SerialName("4. region")
    val region: String?,
    @SerialName("1. symbol")
    val symbol: String?,
    @SerialName("7. timezone")
    val timezone: String?,
    @SerialName("3. type")
    val type: String?
)