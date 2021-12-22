package com.synpulse.companydata.stfrontentengchallenge.Domain.module

import androidx.paging.PagingData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData

data class HomeGlobalQouteData(
    val title: String,
    val category_type: SectionType,
    val companyData: PagingData<CompanyListData>?=null
)
enum class SectionType{
    HEADER,
    TITLE,
    ROW
}
