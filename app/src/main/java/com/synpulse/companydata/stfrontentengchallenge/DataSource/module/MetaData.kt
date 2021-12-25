package com.synpulse.companydata.stfrontentengchallenge.DataSource.module

import com.google.gson.annotations.SerializedName


data class MetaData(
    @SerializedName("1. Information")
    val information: String?,
    @SerializedName("3. Last Refreshed")
    val lastRefreshed: String?,
    @SerializedName("4. Output Size")
    val outputSize: String?,
    @SerializedName("2. Symbol")
    val symbol: String?,
    @SerializedName("5. Time Zone")
    val timeZone: String?
)