package com.mobile.data.usage.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.data.usage.Domain.Model.MobileDataDomain

@Dao
interface RoomDataAccessObejct {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mMobileDataDomain: MobileDataDomain)

    @Query("select _id,SUM(volume_of_mobile_data) as volume_of_mobile_data , MIN(volume_of_mobile_data) as min_volume_of_mobile_data, year , quarter from MobileDataDomain WHERE year>2007 group by year")
    fun getAllMobileDatas(): List<MobileDataDomain>

    @Query("select _id,SUM(volume_of_mobile_data) as volume_of_mobile_data , MIN(volume_of_mobile_data) as min_volume_of_mobile_data, year , quarter from MobileDataDomain WHERE year =:selectYear group by year")
    fun getAllMobileDatasByYear(selectYear: String): List<MobileDataDomain>


    @Query("select * from MobileDataDomain WHERE year =:selectYear group by quarter")
    fun getSelectedYearData(selectYear: String): List<MobileDataDomain>

    @Query("DELETE FROM MobileDataDomain")
    fun deleteTable()
}