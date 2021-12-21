package com.synpulse.companydata.stfrontentengchallenge.DataSource.module


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaData(
    @SerialName("1. Information")
    val information: String?,
    @SerialName("3. Last Refreshed")
    val lastRefreshed: String?,
    @SerialName("4. Output Size")
    val outputSize: String?,
    @SerialName("2. Symbol")
    val symbol: String?,
    @SerialName("5. Time Zone")
    val timeZone: String?
)