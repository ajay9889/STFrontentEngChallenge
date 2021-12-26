package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.SectionType
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote
import com.synpulse.companydata.stfrontentengchallenge.Domain.repository.FinancialDataReposity
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.GainersLosersItemViewHolder
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.TitleItemViewHolder
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders.WatchItemViewHolder

class WatchListAdapter(private val context: Context,
                       private val itemList: List<Any>,
                       private val onClickItems: ((CompanyListData)->Unit)? =null
                        ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        itemList.get(position).let {
            if(it is TbGlobalQuote){
                return 0
            }else{
                return 1
            }
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  when(viewType){
            0->{
                GainersLosersItemViewHolder(parent,onClickItems)
            }
            else->{
                // dashboard rows
                WatchItemViewHolder(parent,onClickItems)
            }
        }
    }
    override fun getItemCount(): Int {
      return itemList.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is GainersLosersItemViewHolder->{
                itemList.get(position)?.let { (holder).bindView(it as TbGlobalQuote) }
            }
            is WatchItemViewHolder->{
                itemList.get(position)?.let { (holder).bindView( it as CompanyListData) }
            }
        }
    }

}