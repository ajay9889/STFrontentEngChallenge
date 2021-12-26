package com.mobile.data.usage.Database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote

@Database(entities = [CompanyListData::class, TbGlobalQuote::class], version = 2, exportSchema = false)
abstract class Databasehelper: RoomDatabase() {
    abstract fun RoomDataAccessObejct(): RoomDataAccessObejct
    companion object{
         fun getDatabase(context: Context): Databasehelper {
            return Room.databaseBuilder(context,Databasehelper::class.java, "FinancialDataDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries()
                .build()
        }
    }
}