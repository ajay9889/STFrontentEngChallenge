package com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData.Companion.toHomeGlobalDomain
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQuote
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQuote.Companion.toTbGlobalQoute
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.repository.FinancialDataReposityImpl
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.HomeGlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.SectionType
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.TbGlobalQuote.Companion.toHomeGlobalDomain
import com.synpulse.companydata.stfrontentengchallenge.MainApplication
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class HomeViewModel(val application: MainApplication, val finRepository : FinancialDataReposityImpl): AndroidViewModel(application){
    val dbInstance : Databasehelper by KoinJavaComponent.inject(Databasehelper::class.java)
    val getTimeSerieseData =MutableLiveData<ViewState<TimeSerieseData>>()
    val getGlobalQoutes =MutableLiveData<GlobalQouteData>()



    @SuppressLint("CheckResult")
    fun getGlobalQoutes(symbol:String){
        finRepository.getQuoteEndpoint(symbol).
        subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ globalQoute ->
                getGlobalQoutes.postValue(globalQoute)
            }
            )
    }
    fun getTimeSeriesData(symbol:String){
        viewModelScope.launch(Dispatchers.IO) {
            finRepository.getDailyData(symbol).let {
                getTimeSerieseData.postValue(it)
            }
        }
    }


    fun insertGlobalQouteInDb(globalQuote: GlobalQuote) {
        globalQuote.symbol?.let {symbol->
            globalQuote.latestTradingDay?.let {tdate->
                dbInstance.RoomDataAccessObejct().getUpdateItems(tdate ,symbol)
            }
            dbInstance.RoomDataAccessObejct().insertTBGlobalQoute(globalQuote.toTbGlobalQoute())
        }
    }



    // fetching data paging source
    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )

    // deprycated
    fun getPaggingSourceData_old() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            dbInstance.RoomDataAccessObejct().getFollowedCompany("1")
        }
    ).flowable.cachedIn(viewModelScope).map { pager->pager.map {
               it.toHomeGlobalDomain()
        }
    }


    fun getPaggingSourceData() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            WatchListPagingSource (dbInstance)
        }
    ).flowable.cachedIn(viewModelScope)


    class WatchListPagingSource(val dbInstance:Databasehelper): PagingSource<Int, HomeGlobalQouteData>() {

        override fun getRefreshKey(state: PagingState<Int, HomeGlobalQouteData>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeGlobalQouteData> {
            val itemList = ArrayList<HomeGlobalQouteData>()
            dbInstance.RoomDataAccessObejct().getGlobalQuoteChanges().let {
                if(it.size>0){
                        itemList.add(
                            HomeGlobalQouteData(
                                title = "Gainers and Losers",
                                category_type = SectionType.TITLE,
                                companyData = null,
                            )
                        )
                        it.map {
                            itemList.add(it.toHomeGlobalDomain())
                        }
                    }
            }
            dbInstance.RoomDataAccessObejct().isGlobalDataContains().let {
                if(it.size>0){
                    itemList.add(
                        HomeGlobalQouteData(
                            title = "You WatchList",
                            category_type =SectionType.TITLE,
                            companyData = null,
                        )
                    )
                    it.map {
                        itemList.add(it.toHomeGlobalDomain())
                    }
                }
            }
            return LoadResult.Page(
                data = itemList ,
                prevKey = null,
                nextKey = null
            )
        }
    }





}