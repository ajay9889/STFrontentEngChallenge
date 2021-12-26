package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.graphics.Color
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseViewHolder
import com.synpulse.companydata.stfrontentengchallenge.Core.Util.Utils
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain.Companion.toCompanyListDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.ItemCompanylistBinding
import com.synpulse.companydata.stfrontentengchallenge.databinding.ItemTitleBinding
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent
import java.util.*

class TitleItemViewHolder (viewGroup: ViewGroup): BaseViewHolder <ItemTitleBinding> (viewGroup ,ItemTitleBinding::inflate ) {
    val dbInstance : Databasehelper by KoinJavaComponent.inject(Databasehelper::class.java)
    fun bindView(homeGlobalQouteData: HomeGlobalQouteData){
        with(viewBinding){
            title.text = homeGlobalQouteData.title
            seaAll.setOnClickListener {
                DsAlert.showToastMessage(viewGroup.context, viewGroup.context.getString(R.string.show_comming_soon_message))
            }
        }
    }
}