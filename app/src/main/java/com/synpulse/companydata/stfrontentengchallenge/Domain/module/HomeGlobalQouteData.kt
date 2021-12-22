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
//
//private fun setupRecyclerView() = with(viewbinding) {
//    adapter = DashboardAdapter(this@DashboardFragment)
//
//    var items = ArrayList<Any>()
//    var dashboardTopSubItems = ArrayList<CompanyListItemData>()
//    dashboardTopSubItems.add(CompanyListItemData("AMZN", "Amazon.com Inc", "+15"))
//    dashboardTopSubItems.add(CompanyListItemData("AAPL", "Apple Inc", "+15"))
//    dashboardTopSubItems.add(CompanyListItemData("XOM", "Exxon Mobil Corp", "+15"))
//    dashboardTopSubItems.add(CompanyListItemData("MSFT", "Microsoft Company", "+15"))
//    dashboardTopSubItems.add(CompanyListItemData("IBM", "International Business Machines Corporation", "-15"))
//
//    var dashboardTopItem = DashboardTopItem("Gainer and Losser", dashboardTopSubItems)
//    items.add(dashboardTopItem)
//
//    items.add("Your WatchLlst")
//
//    for(i in 0..9){
//        items.add(CompanyListItemData("MSFT", "Microsoft Company", "+15"))
//        items.add(CompanyListItemData("IBM", "International Business Machines Corporation", "-15"))
//        items.add(CompanyListItemData("AMZN", "Amazon.com Inc", "+15"))
//    }
//
//    adapter.setItems(items)
//
//    recyclerviewDashboardHome.layoutManager = LinearLayoutManager(requireContext())
//    recyclerviewDashboardHome.adapter = adapter
//
//}
