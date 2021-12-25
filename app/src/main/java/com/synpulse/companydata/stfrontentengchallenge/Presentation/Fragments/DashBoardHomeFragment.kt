package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard.HomeViewModel
import com.synpulse.companydata.stfrontentengchallenge.databinding.HomeFragmentBinding
import org.koin.android.ext.android.inject


class DashBoardHomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    var dialog: ProgressDialog? = null
    val searchViewModel: HomeViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUPView();
        observeLiveData()
    }

    fun setUPView(){

    }
    fun observeLiveData(){

    }

}