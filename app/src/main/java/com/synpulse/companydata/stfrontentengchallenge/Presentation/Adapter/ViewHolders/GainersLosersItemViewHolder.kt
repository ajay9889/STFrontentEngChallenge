package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.synpulse.companydata.Core.base.BaseViewHolder
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote.Companion.toGlobalQoutes
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.ItemGanerloaserBinding

class GainersLosersItemViewHolder (viewGroup: ViewGroup,
                                   val clickItemList:((CompanyListData)->Unit)?=null): BaseViewHolder <ItemGanerloaserBinding> (viewGroup ,ItemGanerloaserBinding::inflate ) {
    @SuppressLint("CheckResult")
    fun bindView( homeGlobalQouteData: HomeGlobalQouteData){
        with(viewBinding){
            homeGlobalQouteData.tbGlobalQuote?.changePercent?.let {
                changesPerc.text = it
                if(it.contains("-".toRegex())){
                    changesPerc.setTextColor(ContextCompat.getColor(viewGroup.context, R.color.red))
                }
            }
            title.text = homeGlobalQouteData.tbGlobalQuote?.symbol
            mainItems.setOnClickListener {
                homeGlobalQouteData.tbGlobalQuote?.let { it1 -> clickItemList?.invoke(it1.toGlobalQoutes()) }
            }
        }
    }

}