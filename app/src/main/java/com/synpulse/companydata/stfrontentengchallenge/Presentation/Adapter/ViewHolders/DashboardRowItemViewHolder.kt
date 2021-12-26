package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.Core.base.BaseViewHolder
import com.synpulse.companydata.stfrontentengchallenge.Core.Util.Utils
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.DashboardData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.SectionType
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.WatchListAdapter
import com.synpulse.companydata.stfrontentengchallenge.databinding.ItemDahsboardRowBinding
import org.koin.java.KoinJavaComponent

class DashboardRowItemViewHolder (viewGroup: ViewGroup, val companyItemSelections:((CompanyListData)->Unit)?=null): BaseViewHolder <ItemDahsboardRowBinding> (viewGroup ,ItemDahsboardRowBinding::inflate ) {
    val dbInstance : Databasehelper by KoinJavaComponent.inject(Databasehelper::class.java)
    fun bindView(dashboardData: DashboardData){
        with(viewBinding){
            if(dashboardData.category_type == SectionType.GAINERS){
                dsRecyclerView.layoutManager=LinearLayoutManager(viewGroup.context, LinearLayoutManager.HORIZONTAL, false)
                (dsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                dsRecyclerView.adapter= WatchListAdapter(viewGroup.context, dashboardData.itemList!!,   companyItemSelections)
            }else{
                dsRecyclerView.layoutManager=LinearLayoutManager(viewGroup.context, LinearLayoutManager.VERTICAL, false)
                (dsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                dsRecyclerView.adapter= WatchListAdapter(viewGroup.context, dashboardData.itemList!!,   companyItemSelections)
            }
        }
    }

}