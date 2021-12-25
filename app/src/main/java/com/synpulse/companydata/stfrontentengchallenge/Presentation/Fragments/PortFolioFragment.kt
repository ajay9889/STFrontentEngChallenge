package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.view.View
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.databinding.PorfolioFragmentBinding

class PortFolioFragment : BaseFragment<PorfolioFragmentBinding>(PorfolioFragmentBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}