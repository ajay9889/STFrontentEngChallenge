package com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard
import android.util.Log
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
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.BestMatche.Companion.getToCompanyDomain
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData.Companion.toCompanyItemDomain
import com.synpulse.companydata.stfrontentengchallenge.DataSource.repository.FinancialDataReposityImpl
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain
import com.synpulse.companydata.stfrontentengchallenge.MainApplication
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SearchViewModel(val application: MainApplication,
                      val finRepository : FinancialDataReposityImpl,
                      ): AndroidViewModel(application){
    val dbInstance : Databasehelper by inject(Databasehelper::class.java)
    val getDefaultCompanyList = MutableLiveData<List<CompanyDataItemDomain>>()

    fun getSearchCompanyName(keyword: String?){
        viewModelScope.launch(Dispatchers.IO) {
            if (keyword.isNullOrBlank()) {
                val list=finRepository.getCompanyList().map {
                    it.toCompanyItemDomain(
                        BehaviorSubject.create(),
                        dbInstance
                    )
                }
                Log.d("com: list" , "$list")
                getDefaultCompanyList.postValue(list)
            } else {
                val listItems = arrayListOf<CompanyDataItemDomain>()
                finRepository.getSearchEndpoint(keyword)?.let {
                    it.data?.bestMatches?.map {
                        it?.getToCompanyDomain()?.let {it1->
                          listItems.add(it1.toCompanyItemDomain(
                              BehaviorSubject.create(),
                              dbInstance
                          ))
                        }
                    }
                }
                getDefaultCompanyList.postValue(listItems.toList()) ;
            }
        }
    }
    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getPaggingSourceData(mCList: List<CompanyDataItemDomain>) = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            CompanyListPagingSource(mCList)
        }
    ).flowable.cachedIn(viewModelScope)


    class CompanyListPagingSource(val mCList: List<CompanyDataItemDomain>
    ): PagingSource<Int, CompanyDataItemDomain>() {

        override fun getRefreshKey(state: PagingState<Int, CompanyDataItemDomain>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompanyDataItemDomain> {
            return LoadResult.Page(
                data = mCList ,
                prevKey = null,
                nextKey = null
            )
        }
    }
}