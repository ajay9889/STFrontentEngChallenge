package com.synpulse.companydata.stfrontentengchallenge.Domain.module
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData.Companion.toCompanyItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain.Companion.toCompanyNormalItemDomain
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

data class CompanyDataItemDomain(
    val symbol: String,
    val name: String,
    val gainloss: String="",
    val iconImage: String,
    val isFollwoing: String,
    val tradeDate: String,
    val behaviourObject: BehaviorSubject<CompanyListData>,
): Serializable{
    companion object{
        fun CompanyDataItemDomain.toCompanyListDomain(dbInstance: Databasehelper)=CompanyListData(
            symbol = this.symbol,
            name =this.name,
            gainloss =this.gainloss,
            iconImage =this.iconImage,
            trade_date = this.tradeDate,
            isFollwoing = dbInstance.RoomDataAccessObejct().getInsertedItems(this.symbol)?.let {
                            if(it.isFollwoing.equals("0")) "1" else "0"
                        }?: kotlin.run {
                           "1"
                        },
        )

        fun CompanyDataItemDomain.toCompanyNormalItemDomain()=CompanyListData(
            symbol = this.symbol,
            name =this.name,
            gainloss =this.gainloss,
            iconImage =this.iconImage,
            isFollwoing = this.isFollwoing,
            trade_date = this.tradeDate
        )
    }
}
