package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.DashboardData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.SectionType
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.*

class DashboardListAdapter(private val context: Context,
                           private val onClickItems: ((CompanyListData)->Unit)? =null
): PagingDataAdapter<DashboardData, RecyclerView.ViewHolder> (DataDifferentiator){
    object DataDifferentiator: DiffUtil.ItemCallback<DashboardData>(){
        override fun areItemsTheSame(oldItem: DashboardData, newItem: DashboardData): Boolean {
            return oldItem.itemList == newItem.itemList
        }
        override fun areContentsTheSame(oldItem: DashboardData, newItem: DashboardData): Boolean {
            return oldItem.itemList == newItem.itemList
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            if(it.category_type == SectionType.TITLE)
                return 0
            else{
                return 1
            }
        }
        return 2
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TitleItemViewHolder ->{
                getItem(position)?.let { holder.bindView(it) }
            }

            is DashboardRowItemViewHolder ->{
                getItem(position)?.let { (holder).bindView( it) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("viewType" ,"$viewType")
        return  when(viewType){
            0->{
                TitleItemViewHolder(parent)
            }
            else->{
                DashboardRowItemViewHolder(parent,onClickItems)
            }
        }
    }
}