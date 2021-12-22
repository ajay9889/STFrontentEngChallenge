package com.mobile.data.usage.Database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData

@Database(entities = [CompanyListData::class], version = 1, exportSchema = false)
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