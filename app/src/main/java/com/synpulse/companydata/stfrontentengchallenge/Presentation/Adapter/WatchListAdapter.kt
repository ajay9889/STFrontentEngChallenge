package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.SectionType
import com.synpulse.companydata.stfrontentengchallenge.Domain.repository.FinancialDataReposity
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.TitleItemViewHolder
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.WatchItemViewHolder

class WatchListAdapter(private val context: Context,
                       private val reposity: FinancialDataReposity,
                       private val onClickItems: ((CompanyListData)->Unit)? =null
                        ): PagingDataAdapter<HomeGlobalQouteData, RecyclerView.ViewHolder> (DataDifferentiator){
    object DataDifferentiator: DiffUtil.ItemCallback<HomeGlobalQouteData>(){
        override fun areItemsTheSame(oldItem: HomeGlobalQouteData, newItem: HomeGlobalQouteData): Boolean {
            return oldItem.companyData == newItem.companyData
        }
        override fun areContentsTheSame(oldItem: HomeGlobalQouteData, newItem: HomeGlobalQouteData): Boolean {
            return oldItem.companyData == newItem.companyData
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return if(it.category_type == SectionType.TITLE) 0 else 1
        }
        return super.getItemViewType(position)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
           is TitleItemViewHolder->{
               getItem(position)?.let { holder.bindView(it) }
           }
            is WatchItemViewHolder->{
                getItem(position)?.let { (holder).bindView(reposity, it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return  when(viewType){
            0->{
                TitleItemViewHolder(parent)
            }
            else->{
                WatchItemViewHolder(parent,onClickItems)
            }
        }
    }
}