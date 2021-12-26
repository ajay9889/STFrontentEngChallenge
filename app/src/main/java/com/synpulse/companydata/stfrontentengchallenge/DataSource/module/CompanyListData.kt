package com.synpulse.companydata.stfrontentengchallenge.DataSource.module

import androidx.room.Entity
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData.Companion.toCompanyListDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain.Companion.toCompanyListDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain.Companion.toCompanyNormalItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.SectionType
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

@Entity(tableName = "CompanyListData", primaryKeys = ["symbol"] )
data class CompanyListData(
    val symbol: String,
    val name: String,
    val gainloss: String="",
    val iconImage: String,
    val isFollwoing: String,
    val trade_date: String,
): Serializable{
    companion object{
        // to manage the follow and unfollow
        fun CompanyListData.toCompanyListDomain(dbInstance: Databasehelper)=CompanyListData(
            symbol = this.symbol,
            name =this.name,
            gainloss =this.gainloss,
            iconImage =this.iconImage,
            trade_date = this.trade_date,
            isFollwoing = dbInstance.RoomDataAccessObejct().getInsertedItems(this.symbol)?.let {
                if(it.isFollwoing.equals("0")) "1" else "0"
            }?: kotlin.run {
                "1"
            },
        )

        fun CompanyListData.toCompanyItemDomain(behaviourObject: BehaviorSubject<CompanyListData>,dbInstance: Databasehelper)= CompanyDataItemDomain(
                 symbol =this.symbol,
                 name =this.name,
                 gainloss =this.gainloss,
                 iconImage =this.iconImage,
                 tradeDate = this.trade_date,
                isFollwoing = dbInstance.RoomDataAccessObejct().getInsertedItems(this.symbol)?.let {
                    it.isFollwoing
                }?: kotlin.run {
                    "0"
                },
                 behaviourObject =behaviourObject,
        )
    }
}
