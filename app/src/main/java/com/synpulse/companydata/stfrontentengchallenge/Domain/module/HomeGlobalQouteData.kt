package com.synpulse.companydata.stfrontentengchallenge.Domain.module
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import io.reactivex.subjects.BehaviorSubject

data class HomeGlobalQouteData(
    val title: String,
    val category_type: SectionType,
    val tbGlobalQuote: TbGlobalQuote?=null,
    val companyData: CompanyListData?=null,
)
enum class SectionType{
    GAINERS,
    WATCHLIST,
    TITLE,
    ROW
}
