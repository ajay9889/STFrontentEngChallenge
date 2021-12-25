package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.CompanyItemViewHolder

class CompanyListAdapter (private val context: Context,
private val itemClicked:((CompanyListData)->Unit)?=null): PagingDataAdapter<CompanyDataItemDomain, RecyclerView.ViewHolder> (DataDifferentiator){
    object DataDifferentiator: DiffUtil.ItemCallback<CompanyDataItemDomain>(){
        override fun areItemsTheSame(oldItem: CompanyDataItemDomain, newItem: CompanyDataItemDomain): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: CompanyDataItemDomain, newItem: CompanyDataItemDomain): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as CompanyItemViewHolder).bindView(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompanyItemViewHolder(parent,itemClicked)
    }
}