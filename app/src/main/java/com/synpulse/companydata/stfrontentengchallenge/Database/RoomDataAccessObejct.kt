package com.mobile.data.usage.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import kotlinx.coroutines.flow.Flow


@Dao
interface RoomDataAccessObejct {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mCompanyListData: List<CompanyListData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(mCompanyListData: CompanyListData)

    @Query("SELECT * FROM CompanyListData where symbol=:symbol")
    fun isFollowing(symbol: String): LiveData<CompanyListData>

    @Query("SELECT * FROM CompanyListData where symbol=:symbol")
    fun getInsertedItems(symbol: String): CompanyListData?

    @Query("DELETE FROM CompanyListData")
    fun deleteTable()
}