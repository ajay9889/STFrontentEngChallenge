package com.mobile.data.usage.Database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull


@Dao
interface RoomDataAccessObejct {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mCompanyListData: List<CompanyListData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTBGlobalQoute(mTbGlobalQuote: TbGlobalQuote)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(mCompanyListData: CompanyListData)

    @Query("SELECT * FROM CompanyListData where symbol=:symbol")
    fun isFollowing(symbol: String): LiveData<CompanyListData>

    @Query("SELECT * FROM CompanyListData where symbol=:symbol")
    fun getInsertedItems(symbol: String): CompanyListData?

    @Query("DELETE FROM CompanyListData")
    fun deleteTable()

    @Query("SELECT * FROM CompanyListData where isFollwoing=:isFollwoing")
    fun getFollowedCompany(isFollwoing: String): PagingSource<Int, CompanyListData>

    @Query("SELECT * FROM CompanyListData LIMIT 1")
    fun isDataChanged(): LiveData<CompanyListData>

    @Query("SELECT * FROM CompanyListData where isFollwoing='1'")
    fun isGlobalDataContains(): List<CompanyListData>

    @Query("SELECT * FROM TbGlobalQuote")
    fun getGlobalQuoteChanges(): List<TbGlobalQuote>


    @NotNull
    @Query("UPDATE CompanyListData SET trade_date = :trade_date where symbol=:symbol")
    fun getUpdateItems(trade_date: String, symbol: String)

}