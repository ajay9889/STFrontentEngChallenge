package com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.graphics.Color
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.Core.base.BaseViewHolder
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain
import com.synpulse.companydata.stfrontentengchallenge.Domain.module.CompanyDataItemDomain.Companion.toCompanyListDomain
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.ItemCompanylistBinding
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent
import java.util.*

class CompanyItemViewHolder (viewGroup: ViewGroup, val companyItemSelections:((CompanyDataItemDomain)->Unit)?=null): BaseViewHolder <ItemCompanylistBinding> (viewGroup ,ItemCompanylistBinding::inflate ) {
    val dbInstance : Databasehelper by KoinJavaComponent.inject(Databasehelper::class.java)
    fun bindView(companyListData: CompanyDataItemDomain){
        with(viewBinding){
            title.text = companyListData.name
            val myColor = Random()
            val color =Color.rgb(myColor.nextInt(255), myColor.nextInt(255), myColor.nextInt(255))
            title.setTextColor(color)
            compositeDisposable.add(
                companyListData.behaviourObject.toFlowable(BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
                    .doOnNext {
                        dbInstance.RoomDataAccessObejct().insertSingle(it)
                    }.observeOn(AndroidSchedulers.mainThread())
                    .subscribe {it->
                        it?.let {
                            with(viewBinding){
                                if(it.isFollwoing.equals("1")) {
                                    follow.setTextColor(ContextCompat.getColor(viewGroup.context, R.color.black))
                                    follow.text = viewGroup.context.resources.getString(R.string.followed)
                                } else {
                                    follow.setTextColor(ContextCompat.getColor(viewGroup.context, R.color.red))
                                    follow.text = viewGroup.context.resources.getString(R.string.add_follow)
                                }
                            }
                        }
                    }
            )

            follow.setOnClickListener {
                val singleItems=companyListData.toCompanyListDomain(dbInstance)
                companyListData.behaviourObject.onNext(singleItems)
            }
            if(companyListData.isFollwoing.equals("1")) {
                follow.setTextColor(ContextCompat.getColor(viewGroup.context, R.color.black))
                follow.text = viewGroup.context.resources.getString(R.string.followed)
            } else {
                follow.setTextColor(ContextCompat.getColor(viewGroup.context, R.color.red))
                follow.text = viewGroup.context.resources.getString(R.string.add_follow)
            }
        }
    }

}