package com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQuote
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQuote.Companion.toTbGlobalQoute
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.repository.FinancialDataReposityImpl
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.DashboardData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.SectionType
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

    fun getPaggingSourceData() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            WatchListPagingSource (dbInstance)
        }
    ).flowable.cachedIn(viewModelScope)

class WatchListPagingSource(val dbInstance:Databasehelper): PagingSource<Int, DashboardData>() {
        override fun getRefreshKey(state: PagingState<Int, DashboardData>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DashboardData> {
            val dashbboardData = ArrayList<DashboardData>()
            dbInstance.RoomDataAccessObejct().getGlobalQuoteChanges().let {
                if(it.size>0){
                   dashbboardData.add(DashboardData(title = "Gainers and Losers",
                            category_type = SectionType.TITLE,
                            itemList = null))

                    dashbboardData.add(DashboardData(
                            title =  SectionType.GAINERS.name,
                            category_type = SectionType.GAINERS,
                            itemList = it,
                        )
                    )
                 }
            }
            dbInstance.RoomDataAccessObejct().isGlobalDataContains().let {
                if(it.size>0){
                    dashbboardData.add(DashboardData(title = "Your WatchList",
                            category_type = SectionType.TITLE,
                            itemList = null))
                    dashbboardData.add(DashboardData(
                            title = SectionType.WATCHLIST.name,
                            category_type = SectionType.WATCHLIST,
                            itemList = it,
                        )
                    )
                }
            }
            return LoadResult.Page(
                data = dashbboardData ,
                prevKey = null,
                nextKey = null
            )
        }
    }
}