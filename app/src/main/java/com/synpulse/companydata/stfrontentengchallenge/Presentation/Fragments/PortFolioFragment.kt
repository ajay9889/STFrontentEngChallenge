package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.view.View
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.PorfolioFragmentBinding
import org.koin.android.ext.android.inject

class PortFolioFragment : BaseFragment<PorfolioFragmentBinding>(PorfolioFragmentBinding::inflate) {
    val userSignInViewModel: UserSignInViewModel by inject()
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
                DsAlert.showAlertLogout(requireActivity(),userSignInViewModel, getString(R.string.logout_warn),
                    requireContext().resources.getString(R.string.logout_msg),"Yes")
            }
        }
    }
}