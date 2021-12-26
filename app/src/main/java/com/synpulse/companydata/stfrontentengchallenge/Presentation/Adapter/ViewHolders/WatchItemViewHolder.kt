package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.Core.base.BaseViewHolder
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.repository.FinancialDataReposity
import com.synpulse.companydata.stfrontentengchallenge.databinding.ItemDsrowBinding
import org.koin.java.KoinJavaComponent

class WatchItemViewHolder (viewGroup: ViewGroup,
     val clickItemList:((CompanyListData)->Unit)?=null): BaseViewHolder <ItemDsrowBinding> (viewGroup ,ItemDsrowBinding::inflate ) {
    @SuppressLint("CheckResult")
    fun bindView( companyData: CompanyListData){
        with(viewBinding){
            gain.text = companyData?.gainloss
            name.text = companyData?.name
            symbol.text = companyData?.symbol
            date.text = "Trading Date: ${companyData?.trade_date}"
            linItem.setOnClickListener {
                clickItemList?.invoke(companyData!!)
            }
        }
    }
}