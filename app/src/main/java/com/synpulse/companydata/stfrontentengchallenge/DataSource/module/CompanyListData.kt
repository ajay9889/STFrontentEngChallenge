package com.synpulse.companydata.stfrontentengchallenge.DataSource.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CompanyListData", primaryKeys = ["name"] )
data class CompanyListData(
    val symbol: String,
    val name: String,
    val gainloss: String="",
    val iconImage: String,
    val isFollwoing: String,
)
