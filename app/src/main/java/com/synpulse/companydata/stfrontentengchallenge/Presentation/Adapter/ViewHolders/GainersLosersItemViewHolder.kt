package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.synpulse.companydata.Core.base.BaseViewHolder
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote.Companion.toGlobalQoutes
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.ItemGanerloaserBinding

class GainersLosersItemViewHolder (viewGroup: ViewGroup,
                                   val clickItemList:((CompanyListData)->Unit)?=null): BaseViewHolder <ItemGanerloaserBinding> (viewGroup ,ItemGanerloaserBinding::inflate ) {
    @SuppressLint("CheckResult")
    fun bindView( tbGlobalQuote: TbGlobalQuote){
        with(viewBinding){
            tbGlobalQuote.changePercent?.let {
                changesPerc.text = it
                if(it.contains("-".toRegex())){
                    changesPerc.setTextColor(ContextCompat.getColor(viewGroup.context, R.color.red))
                }
            }
            date.text = "Last trading date on \n${tbGlobalQuote?.latestTradingDay}"
            title.text = tbGlobalQuote?.symbol
            mainItems.setOnClickListener {
                tbGlobalQuote?.let { it1 -> clickItemList?.invoke(it1.toGlobalQoutes()) }
            }
        }
    }

}