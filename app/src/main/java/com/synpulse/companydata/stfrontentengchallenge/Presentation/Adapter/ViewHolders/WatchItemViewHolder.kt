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
    val dbInstance : Databasehelper by KoinJavaComponent.inject(Databasehelper::class.java)
    @SuppressLint("CheckResult")
    fun bindView(reposity: FinancialDataReposity, homeGlobalQouteData: HomeGlobalQouteData){
        with(viewBinding){
            gain.text = homeGlobalQouteData.companyData?.gainloss
            name.text = homeGlobalQouteData.companyData?.name
            symbol.text = homeGlobalQouteData.companyData?.symbol

            /*homeGlobalQouteData.companyData?.let {companyListData->
                homeGlobalQouteData.behaviourObject?.let {
                    compositeDisposable.add(
                        reposity.getQuoteEndpoint(homeGlobalQouteData.companyData.symbol).
                        subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ globalQoute ->
                                with(viewBinding){
                                    gain.text = "$"+globalQoute?.globalQuote?.price
                                    name.text = "Trading day ${globalQoute?.globalQuote?.latestTradingDay}"
                                    symbol.text = homeGlobalQouteData.companyData?.symbol
                                    }
                                }
                            ))
                }
            }*/

            linItem.setOnClickListener {
                clickItemList?.invoke(homeGlobalQouteData.companyData!!)
            }
        }
    }

}