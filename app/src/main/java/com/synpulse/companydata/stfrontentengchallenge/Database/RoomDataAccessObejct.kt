package com.mobile.data.usage.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData

@Dao
interface RoomDataAccessObejct {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mCompanyListData: CompanyListData)

    @Query("DELETE FROM CompanyListData")
    fun deleteTable()
}