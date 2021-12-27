package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.view.View
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.PorfolioFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent

class PortFolioFragment : BaseFragment<PorfolioFragmentBinding>(PorfolioFragmentBinding::inflate) {
    val userSignInViewModel: UserSignInViewModel by inject()
    val dbInstance : Databasehelper by KoinJavaComponent.inject(Databasehelper::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    fun setUpView(){
        with(viewBinding){
            logOut.setOnClickListener {
                DsAlert.showAlertLogout(requireActivity(),
                    userSignInViewModel,
                    dbInstance,
                    getString(R.string.logout_warn),
                    requireContext().resources.getString(R.string.logout_msg),"Yes")
            }
        }
    }
}