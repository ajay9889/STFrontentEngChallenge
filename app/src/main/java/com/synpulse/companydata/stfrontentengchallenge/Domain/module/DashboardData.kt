package com.synpulse.companydata.stfrontentengchallenge.Domain.module
import androidx.paging.PagingData
data class DashboardData(
    val title: String,
    val category_type: SectionType,
    val itemList: List<Any>? = null
)
