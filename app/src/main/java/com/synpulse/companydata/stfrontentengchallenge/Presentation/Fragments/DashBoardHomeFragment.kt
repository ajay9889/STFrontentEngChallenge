package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Core.base.SingleFragmentActivity
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.WatchListAdapter
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard.HomeViewModel
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
       val adapterL= WatchListAdapter(requireContext(),
            homeViewModel.finRepository,
            this::listItemClicked)
        with(viewBinding){
            with(recyclerviewDashboardHome) {
                adapter = adapterL
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }
    }
    fun listItemClicked(companyListData: CompanyListData){
        SingleFragmentActivity.launchFragment(requireContext() ,DetailsFragment.getFragmentInstance(companyListData))
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
                    (recyclerviewDashboardHome.adapter as WatchListAdapter).submitData(
                        lifecycle,
                        pagingdata
                    )
                }
            )
        }
    }

}