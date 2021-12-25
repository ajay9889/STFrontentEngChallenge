package com.synpulse.companydata.stfrontentengchallenge.Domain.module
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import io.reactivex.subjects.BehaviorSubject

data class HomeGlobalQouteData(
    val title: String,
    val category_type: SectionType,
    val companyData: CompanyListData?=null,
    val behaviourObject: BehaviorSubject<CompanyListData>?,
)
enum class SectionType{
    HEADER,
    TITLE,
    ROW
}
