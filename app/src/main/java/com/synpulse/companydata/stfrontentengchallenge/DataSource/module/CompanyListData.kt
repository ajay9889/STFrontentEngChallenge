package com.synpulse.companydata.stfrontentengchallenge.DataSource.module

import androidx.room.Entity
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain.Companion.toCompanyListDomain
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

@Entity(tableName = "CompanyListData", primaryKeys = ["name"] )
data class CompanyListData(
    val symbol: String,
    val name: String,
    val gainloss: String="",
    val iconImage: String,
    val isFollwoing: String,
): Serializable{

    companion object{
        fun CompanyListData.toCompanyItemDomain(behaviourObject: BehaviorSubject<CompanyListData>,dbInstance: Databasehelper)= CompanyDataItemDomain(
                 symbol =this.symbol,
                 name =this.symbol,
                 gainloss =this.gainloss,
                 iconImage =this.iconImage,
                isFollwoing = dbInstance.RoomDataAccessObejct().getInsertedItems(this.symbol)?.let {
                    it.isFollwoing
                }?: kotlin.run {
                    "0"
                },
                 behaviourObject =behaviourObject,
        )
    }
}
