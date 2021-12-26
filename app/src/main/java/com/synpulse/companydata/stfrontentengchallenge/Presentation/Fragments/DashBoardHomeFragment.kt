package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.Core.networkutils.NetworkConnectivity
import com.synpulse.companydata.stfrontentengchallenge.Core.base.SingleFragmentActivity
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.DashboardListAdapter
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard.HomeViewModel
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.HomeFragmentBinding
import org.koin.android.ext.android.inject


class DashBoardHomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    var dialog: ProgressDialog? = null
    val homeViewModel: HomeViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUPView();
        observeLiveData()
        initRecyclerView()
    }
    fun setUPView(){
        dialog = DsAlert.onCreateDialog(requireContext())
        dialog?.show()
        val adapterL= DashboardListAdapter(requireContext(),    this::listItemClicked)
        with(viewBinding.recyclerviewDashboardHome) {
            adapter = adapterL
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }
    fun listItemClicked(companyListData: CompanyListData){
        if(NetworkConnectivity.isNetworkConnected(requireContext())){
            SingleFragmentActivity.launchFragment(requireContext() ,DetailsFragment.getFragmentInstance(companyListData))
        }else{
            DsAlert.showAlert(requireActivity(), getString(R.string.net_error_warning), requireContext().resources.getString(R.string.net_error),"Okay")
        }
    }
    fun observeLiveData(){
        homeViewModel.dbInstance.RoomDataAccessObejct().isDataChanged().observe(viewLifecycleOwner , {
            dialog?.cancel()
            initRecyclerView()
        })
        if(homeViewModel.dbInstance.RoomDataAccessObejct().isGlobalDataContains().size<1)
        SingleFragmentActivity.launchFragment(requireContext() ,SearchFragment.getFragmentInstance())
    }

    @SuppressLint("CheckResult")
    fun initRecyclerView(){
        with(viewBinding){
            homeViewModel.getPaggingSourceData().subscribe(
                { pagingdata ->
                    (recyclerviewDashboardHome.adapter as DashboardListAdapter).submitData(
                        lifecycle,
                        pagingdata
                    )
                }
            )
        }
    }

}