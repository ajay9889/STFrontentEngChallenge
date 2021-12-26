package com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData.Companion.toHomeGlobalDomain
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQouteData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQuote
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.GlobalQuote.Companion.toTbGlobalQoute
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.repository.FinancialDataReposityImpl
import com.synpulse.companydata.stfrontentengchallenge.MainApplication
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class HomeViewModel(val application: MainApplication, val finRepository : FinancialDataReposityImpl): AndroidViewModel(application){
    val dbInstance : Databasehelper by KoinJavaComponent.inject(Databasehelper::class.java)
    val getTimeSerieseData =MutableLiveData<ViewState<TimeSerieseData>>()
    val getGlobalQoutes =MutableLiveData<GlobalQouteData>()

    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getPaggingSourceData() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            dbInstance.RoomDataAccessObejct().getFollowedCompany("1")
        }
    ).flowable.cachedIn(viewModelScope).map { pager->pager.map {
               it.toHomeGlobalDomain(
                    BehaviorSubject.create()
               )
        }
    }

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
            finRepository.getDailyData(symbol)?.let {
                getTimeSerieseData.postValue(it)
            }
        }
    }


    fun insertGlobalQouteInDb(globalQuote: GlobalQuote) {
        globalQuote.symbol?.let {
            dbInstance.RoomDataAccessObejct().getUpdateItems(globalQuote.latestTradingDay!! ,it)
            dbInstance.RoomDataAccessObejct().insertTBGlobalQoute(globalQuote.toTbGlobalQoute())
        }
    }
}